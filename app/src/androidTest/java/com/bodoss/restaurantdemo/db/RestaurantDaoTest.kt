package com.bodoss.restaurantdemo.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.data.SortingValues
import com.bodoss.restaurantdemo.util.LiveDataTestUtil.getValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantDaoTest : DbTestBase() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndLoadTest() {
        val r = Restaurant(1, SortingValues(), "Rest.Name", favourite = false)
        db.restaurantDao().insert(r)
        val loaded = getValue(db.restaurantDao().getAll())
        assertThat(loaded.size, `is`(1))
    }


    @Test
    fun insertUpdateTest() {
        val r = Restaurant(1, SortingValues(), "Rest.Name", favourite = false)
        val r2 = Restaurant(1, SortingValues(), "NewName", favourite = true)
        db.restaurantDao().insert(r)
        db.restaurantDao().insert(r2)
        val loaded = getValue(db.restaurantDao().getAll())
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0].name, equalTo("NewName"))
    }

    @Test
    fun delereTest() {
        val r = Restaurant(1, SortingValues(), "Rest.Name", favourite = false)
        val r2 = Restaurant(2, SortingValues(), "NewName", favourite = true)
        db.restaurantDao().insert(r)
        db.restaurantDao().insert(r2)
        db.restaurantDao().delete(r2)
        val loaded = getValue(db.restaurantDao().getAll())
        assertThat(loaded.size, `is`(1))
        assertThat(loaded[0].name, equalTo("Rest.Name"))
    }
}