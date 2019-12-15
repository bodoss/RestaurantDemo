package com.bodoss.restaurantdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bodoss.restaurantdemo.common.DataBoundListAdapter
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.databinding.RestaurantItemBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val diskIO: ExecutorService = Executors.newSingleThreadExecutor()

data class RestaurantWrap(val restaurant: Restaurant, val sort: String, val sortVal: Float)

class RestaurantsAdapter : DataBoundListAdapter<RestaurantWrap, RestaurantItemBinding>(diskIO,
    object : DiffUtil.ItemCallback<RestaurantWrap>() {
        override fun areItemsTheSame(oldItem: RestaurantWrap, newItem: RestaurantWrap): Boolean {
            return oldItem.restaurant.id == newItem.restaurant.id
        }

        override fun areContentsTheSame(oldItem: RestaurantWrap, newItem: RestaurantWrap): Boolean {
            return oldItem == newItem
        }
    }) {
    var favChanged: ((r: Restaurant, fav: Boolean) -> Unit)? = null

    override fun createBinding(parent: ViewGroup): RestaurantItemBinding {
        return RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: RestaurantItemBinding, item: RestaurantWrap) {
        binding.item = item
        binding.favBtn.setOnClickListener {
            favChanged?.invoke(item.restaurant, !item.restaurant.favourite)
        }
    }
}