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

class RestaurantsAdapter : DataBoundListAdapter<Restaurant, RestaurantItemBinding>(diskIO,
    object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }) {
    var favChanged: ((r: Restaurant, fav: Boolean) -> Unit)? = null

    override fun createBinding(parent: ViewGroup): RestaurantItemBinding {
        return RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: RestaurantItemBinding, item: Restaurant) {
        binding.item = item
        binding.favBtn.setOnClickListener {
            favChanged?.invoke(item, !item.favourite)
        }
    }
}