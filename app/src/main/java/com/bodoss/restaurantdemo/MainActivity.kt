package com.bodoss.restaurantdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bodoss.restaurantdemo.data.db.RestaurantDb
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RestaurantDb.get(this).restaurantDao().getAll().observe(this, Observer {
            Timber.d("loaded from db: ${it.size} items")
        })
    }
}
