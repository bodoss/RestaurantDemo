package com.bodoss.restaurantdemo.data

import com.google.gson.annotations.SerializedName

data class Restaurants(

    @field:SerializedName("restaurants")
    val restaurants: List<Restaurant>? = null
)