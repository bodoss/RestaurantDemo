package com.bodoss.restaurantdemo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bodoss.restaurantdemo.data.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    fun getAll(): LiveData<List<Restaurant>>

    @Insert
    fun insert(items: List<Restaurant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Restaurant)

    @Delete
    fun delete(item: Restaurant)

}