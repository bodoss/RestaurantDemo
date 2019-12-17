package com.bodoss.restaurantdemo.data

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SortTest {

    @Mock
    lateinit var r1: Restaurant
    @Mock
    lateinit var r2: Restaurant
    @Mock
    lateinit var v1: SortingValues
    @Mock
    lateinit var v2: SortingValues


    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Before
    fun init() {
        `when`(r1.sortingValues).thenReturn(v1)
        `when`(r2.sortingValues).thenReturn(v2)
    }

    @Test
    fun testBestMatch() {
        `when`(v1.bestMatch).thenReturn(12.0)
        `when`(v2.bestMatch).thenReturn(50.0)
        val resList = BestMatchSortOption("bestMatch").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.bestMatch!! >= resList[1].sortingValues?.bestMatch!!)
    }

    @Test
    fun testNewest() {
        `when`(v1.newest).thenReturn(455.0)
        `when`(v2.newest).thenReturn(155.0)
        val resList = NewesSortOption("newest").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.newest!! >= resList[1].sortingValues?.newest!!)
    }

    @Test
    fun testRatingAverage() {
        `when`(v1.ratingAverage).thenReturn(3.5)
        `when`(v2.ratingAverage).thenReturn(2.0)
        val resList = RatingAverageSortOption("ratingAverage").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.ratingAverage!! >= resList[1].sortingValues?.ratingAverage!!)
    }

    @Test
    fun testDistance() {
        `when`(v1.distance).thenReturn(1245)
        `when`(v2.distance).thenReturn(2353)
        val resList = DistanceSortOption("distance").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.distance!! <= resList[1].sortingValues?.distance!!)
    }

    @Test
    fun testPopularity() {
        `when`(v1.popularity).thenReturn(2.0)
        `when`(v2.popularity).thenReturn(3.0)
        val resList = PopularitySortOption("popularity").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.popularity!! >= resList[1].sortingValues?.popularity!!)
    }

    @Test
    fun testAverageProductPrice() {
        `when`(v1.averageProductPrice).thenReturn(3456)
        `when`(v2.averageProductPrice).thenReturn(235)
        val resList = AveragePriceSortOption("averageProductPrice").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.averageProductPrice!! <= resList[1].sortingValues?.averageProductPrice!!)
    }

    @Test
    fun testDeliveryCosts() {
        `when`(v1.deliveryCosts).thenReturn(123)
        `when`(v2.deliveryCosts).thenReturn(34)
        val resList = DeliveryCostsSortOption("averageProductPrice").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.deliveryCosts!! <= resList[1].sortingValues?.deliveryCosts!!)
    }

    @Test
    fun testMinCost() {
        `when`(v1.minCost).thenReturn(30)
        `when`(v2.minCost).thenReturn(10)
        val resList = MinCostSortOption("mincost").sort(arrayListOf(r1, r2))
        assert(resList[0].sortingValues?.minCost!! <= resList[1].sortingValues?.minCost!!)
    }

}
