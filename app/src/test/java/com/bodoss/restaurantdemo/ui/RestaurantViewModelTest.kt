package com.bodoss.restaurantdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.repo.RestaurantsRepository
import com.bodoss.restaurantdemo.util.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class RestaurantViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repo = Mockito.mock(RestaurantsRepository::class.java)
    private var vm = RestaurantsVM(repo)

    @Before
    fun init() {

    }

    @Test
    fun testNull() {
        MatcherAssert.assertThat(vm.getListData(), CoreMatchers.notNullValue())
    }

    @Test
    fun getRestaurantsTest() {
        val observer = mock<Observer<List<RestaurantWrap>>>()
        val restRes = MutableLiveData<List<RestaurantWrap>>()
        vm.getListData().observeForever(observer)
        verify(repo).getAllRestaurants()
        val wrap = RestaurantWrap(Restaurant(3, name = "name"), "minCost", 23f)
        val list = arrayListOf(wrap)
        restRes.postValue(list)
        verify(observer).onChanged(list)
    }
}