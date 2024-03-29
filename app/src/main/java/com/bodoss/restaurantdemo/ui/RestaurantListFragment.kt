package com.bodoss.restaurantdemo.ui


import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bodoss.restaurantdemo.Injector
import com.bodoss.restaurantdemo.data.MinCostSortOption
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.data.SortOption
import com.bodoss.restaurantdemo.data.toOpenState
import com.bodoss.restaurantdemo.databinding.FragmentRestaurantListBinding
import com.bodoss.restaurantdemo.repo.RestaurantsRepository


class RestaurantListFragment : Fragment() {

    private val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RestaurantsVM(Injector.getRestaurantsRepo()) as T
        }
    }

    val vm by activityViewModels<RestaurantsVM> { factory }

    lateinit var bind: FragmentRestaurantListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentRestaurantListBinding.inflate(inflater, container, false)
        return bind.root
    }

    private val restAdapter: RestaurantsAdapter = RestaurantsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.vm = vm
        bind.rv.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = restAdapter
        }
        restAdapter.favChanged = vm.favChanged
        vm.getListData().observe(viewLifecycleOwner) {
            restAdapter.submitList(it)
        }

        val spinnerArray = SortOption.sortMap.keys.toTypedArray()
        val spinnerArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(activity!!, R.layout.simple_spinner_item, spinnerArray)
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        bind.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SortOption.sortMap[spinnerArray[position]]?.let {
                    vm.sortOption = it
                }
            }
        }
        bind.spinner.adapter = spinnerArrayAdapter
    }

}

class RestaurantsVM(
    private val repo: RestaurantsRepository
) : ViewModel() {
    val filter = ObservableField<String>()

    private var latestData = ArrayList<Restaurant>()
    private var filterVal = ""
    private val listLiveData = MutableLiveData<List<RestaurantWrap>>()

    var sortOption: SortOption = SortOption.sortMap["deliveryCosts"] ?: MinCostSortOption()
        set(value) {
            field = value
            setNewData()
        }

    val favChanged: ((r: Restaurant, fav: Boolean) -> Unit)

    init {
        favChanged = { r, fav ->
            repo.updateRestaurant(Restaurant(r.id, r.sortingValues, r.name, r.status, fav))
        }
        filter.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                filter.get()?.let {
                    filterVal = it
                    setNewData()
                }
            }
        })
    }

    private fun getRestaurants(): LiveData<List<Restaurant>> {
        return repo.getAllRestaurants()
    }

    private fun setNewListData(data: List<Restaurant>) {
        latestData.clear()
        latestData.addAll(data)
        setNewData()
    }

    fun getListData(): LiveData<List<RestaurantWrap>> {
        return Transformations.switchMap(getRestaurants()) {
            setNewListData(it)
            listLiveData
        }
    }

    private fun setNewData() {
        val r = ArrayList<Restaurant>()
        val list = latestData.filter { it.name?.contains(filterVal, true) == true }
        val res = list.groupBy { it.favourite }
        res[true]?.let { favs ->
            favs.groupBy { it.status }.entries.sortedBy { it.key?.toOpenState() }.forEach { e ->
                r.addAll(sortOption.sort(e.value))
            }
        }
        res[false]?.let { other ->
            other.groupBy { it.status }.entries.sortedBy { it.key?.toOpenState() }.forEach { e ->
                r.addAll(sortOption.sort(e.value))
            }
        }

        listLiveData.postValue(r.map {
            RestaurantWrap(
                it,
                sortOption.sortOption,
                sortOption.getSortVal(it)
            )
        })
    }
}
