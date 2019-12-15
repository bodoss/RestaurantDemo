package com.bodoss.restaurantdemo.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.bodoss.restaurantdemo.Injector
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.databinding.FragmentRestaurantListBinding
import com.bodoss.restaurantdemo.repo.RestaurantsRepository

class RestaurantListFragment : Fragment() {

    val vm by activityViewModels<RestaurantsVM>()
    lateinit var bind: FragmentRestaurantListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentRestaurantListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.vm = vm
        bind.rv.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = vm.adapter
        }
        vm.observe(viewLifecycleOwner)
    }

}

class RestaurantsVM : ViewModel() {
    val repo: RestaurantsRepository
    val adapter = RestaurantsAdapter()
    val filter = ObservableField<String>()

    private var latestData = ArrayList<Restaurant>()
    private var filterVal = ""


    init {
        repo = Injector.getRestaurantsRepo()
        adapter.favChanged = { r, fav ->
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

    fun observe(lifecycleOwner: LifecycleOwner) {
        getRestaurants().observe(lifecycleOwner, Observer { setNewListData(it) })
    }

    private fun setNewListData(data: List<Restaurant>) {
        latestData.clear()
        latestData.addAll(data)
        setNewData()
    }

    private fun setNewData() {
        val r = ArrayList<Restaurant>()
        val list = latestData.filter { it.name?.contains(filterVal, true) == true }
        val res = list.groupBy { it.favourite }
        res[true]?.let {
            r.addAll(it.sortedBy { it.name })
        }
        res[false]?.let {
            r.addAll(it.sortedBy { it.name })
        }
        adapter.submitList(r)
    }
}
