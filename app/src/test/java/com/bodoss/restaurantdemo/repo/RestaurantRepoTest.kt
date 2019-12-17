package com.bodoss.restaurantdemo.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.data.db.RestaurantDao
import com.bodoss.restaurantdemo.data.db.RestaurantDb
import com.bodoss.restaurantdemo.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RestaurantRepoTest {
    private lateinit var repo: RestaurantsRepository
    private val dao = mock(RestaurantDao::class.java)
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(RestaurantDb::class.java)
        `when`(db.restaurantDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repo = RestaurantRepoImpl(dao)
    }

    @Test
    fun getRestaurantsUpdateTest() {
        val allRestResult = MutableLiveData<List<Restaurant>>()
        val observer = mock<Observer<List<Restaurant>>>()
        `when`(dao.getAll()).thenReturn(allRestResult)
        repo.getAllRestaurants().observeForever(observer)
        verify(dao).getAll()
        val rest = Restaurant(2, name = "asdga")
        repo.updateRestaurant(rest)
        Thread.sleep(200)
        verify(dao).insert(rest)
        val list = arrayListOf(rest)
        allRestResult.postValue(list)
        verify(observer).onChanged(list)
    }


}