package com.bodoss.restaurantdemo.repo

import androidx.lifecycle.LiveData
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.data.db.RestaurantDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface RestaurantsRepository {
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    fun updateRestaurant(item: Restaurant)
}

class RestaurantRepoImpl(val dao: RestaurantDao) : RestaurantsRepository {
    override fun getAllRestaurants(): LiveData<List<Restaurant>> {
        return dao.getAll()
    }

    override fun updateRestaurant(item: Restaurant) {
        GlobalScope.launch {
            dao.insert(item)
        }
    }
}