package com.bodoss.restaurantdemo.data

interface SortOption {

    companion object {
        val sortMap = HashMap<String, SortOption>()

        init {
            sortMap["bestMatch"] = BestMatchSortOption("bestMatch")
            sortMap["newest"] = NewesSortOption("newest")
            sortMap["ratingAverage"] = RatingAverageSortOption("ratingAverage")
            sortMap["distance"] = DistanceSortOption("distance")
            sortMap["popularity"] = PopularitySortOption("popularity")
            sortMap["averageProductPrice"] = AveragePriceSortOption("averageProductPrice")
            sortMap["deliveryCosts"] = DeliveryCostsSortOption("deliveryCosts")
            sortMap["minCost"] = MinCostSortOption("minCost")
        }
    }

    val sortOption: String

    fun sort(list: List<Restaurant>): List<Restaurant>
    fun getSortVal(res: Restaurant): Float
}

class MinCostSortOption(override val sortOption: String = "minCost") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedBy { it.sortingValues?.minCost }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.minCost?.toFloat() ?: 0f
    }
}

class BestMatchSortOption(override val sortOption: String = "bestMatch") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedByDescending { it.sortingValues?.bestMatch }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.bestMatch?.toFloat() ?: 0f
    }
}

class NewesSortOption(override val sortOption: String = "newest") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedByDescending { it.sortingValues?.newest }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.newest?.toFloat() ?: 0f
    }
}

class RatingAverageSortOption(override val sortOption: String = "ratingAverage") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedByDescending { it.sortingValues?.ratingAverage }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.ratingAverage?.toFloat() ?: 0f
    }
}


class DistanceSortOption(override val sortOption: String = "distance") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedBy { it.sortingValues?.distance }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.distance?.toFloat() ?: 0f
    }
}

class PopularitySortOption(override val sortOption: String = "popularity") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedByDescending { it.sortingValues?.popularity }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.popularity?.toFloat() ?: 0f
    }
}

class AveragePriceSortOption(override val sortOption: String = "averageProductPrice") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedBy { it.sortingValues?.averageProductPrice }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.averageProductPrice?.toFloat() ?: 0f
    }
}

class DeliveryCostsSortOption(override val sortOption: String = "deliveryCosts") : SortOption {
    override fun sort(list: List<Restaurant>): List<Restaurant> {
        return list.sortedBy { it.sortingValues?.deliveryCosts }
    }

    override fun getSortVal(res: Restaurant): Float {
        return res.sortingValues?.deliveryCosts?.toFloat() ?: 0f
    }
}