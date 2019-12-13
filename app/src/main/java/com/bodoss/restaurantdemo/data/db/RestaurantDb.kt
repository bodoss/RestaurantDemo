package com.bodoss.restaurantdemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bodoss.restaurantdemo.R
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.data.Restaurants
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

@Database(entities = [Restaurant::class], version = 1)
abstract class RestaurantDb : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        private var instance: RestaurantDb? = null
        @Synchronized
        fun get(context: Context): RestaurantDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDb::class.java,
                    "restaurant.db"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        fillInDb(context.applicationContext)
                    }
                }).build()
            }
            return instance!!
        }

        private fun fillInDb(context: Context) {
            val raw: InputStream = context.resources.openRawResource(R.raw.data)
            val rd: Reader = BufferedReader(InputStreamReader(raw))
            val restaurants = Gson().fromJson(rd, Restaurants::class.java)
            restaurants.restaurants?.let {
                GlobalScope.launch {
                    get(context).restaurantDao().insert(it)
                    Timber.d(
                        "parsed and inserted ${restaurants.restaurants?.size}"
                    )
                }
            }
        }
    }
}