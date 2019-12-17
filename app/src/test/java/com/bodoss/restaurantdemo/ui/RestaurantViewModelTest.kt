package com.bodoss.restaurantdemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bodoss.restaurantdemo.data.Restaurant
import com.bodoss.restaurantdemo.repo.RestaurantsRepository
import com.bodoss.restaurantdemo.util.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RestaurantViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repo = Mockito.mock(RestaurantsRepository::class.java)
    private var vm = RestaurantsVM(repo)

    @Test
    fun testNull() {
        MatcherAssert.assertThat(vm.getListData(), CoreMatchers.notNullValue())
    }

    @Test
    fun getRestaurantsTest() {
        val observer = mock<Observer<List<RestaurantWrap>>>()
        val restRes = MutableLiveData<List<RestaurantWrap>>()
        val spyVm = spy(vm)
        `when`(spyVm.getListData()).thenReturn(restRes)
        spyVm.getListData().observeForever(observer)
        verify(repo).getAllRestaurants()
    }

    @Test
    fun favouriteTest() {
        val rest = Restaurant(3, name = "name", favourite = true)
        vm.favChanged.invoke(rest, true)
        verify(repo).updateRestaurant(rest)
    }

}