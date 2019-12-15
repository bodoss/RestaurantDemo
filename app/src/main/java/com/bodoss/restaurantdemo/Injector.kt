package com.bodoss.restaurantdemo

import com.bodoss.restaurantdemo.data.db.RestaurantDb
import com.bodoss.restaurantdemo.repo.RestaurantRepoImpl

object Injector {
    fun getAppContext() = App.get()

    fun getDB() = RestaurantDb.get(getAppContext())

    fun getRestaurantsRepo() = RestaurantRepoImpl(getDB().restaurantDao())
}