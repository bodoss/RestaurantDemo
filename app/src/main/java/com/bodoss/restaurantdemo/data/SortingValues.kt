package com.bodoss.restaurantdemo.data

import com.google.gson.annotations.SerializedName

data class SortingValues(

	@field:SerializedName("averageProductPrice")
	val averageProductPrice: Int? = null,

	@field:SerializedName("bestMatch")
	val bestMatch: Double? = null,

	@field:SerializedName("distance")
	val distance: Int? = null,

	@field:SerializedName("deliveryCosts")
	val deliveryCosts: Int? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("newest")
	val newest: Double? = null,

	@field:SerializedName("minCost")
	val minCost: Int? = null,

	@field:SerializedName("ratingAverage")
	val ratingAverage: Double? = null
)