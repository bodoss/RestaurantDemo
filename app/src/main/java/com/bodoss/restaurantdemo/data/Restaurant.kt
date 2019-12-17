package com.bodoss.restaurantdemo.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Restaurant(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @Embedded
    @field:SerializedName("sortingValues")
    val sortingValues: SortingValues? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    val favourite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurant

        if (id != other.id) return false
        if (sortingValues != other.sortingValues) return false
        if (name != other.name) return false
        if (status != other.status) return false
        if (favourite != other.favourite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (sortingValues?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + favourite.hashCode()
        return result
    }
}

fun String?.toOpenState(): OpenState {
    return when (this) {
        "open" -> OpenState.OPEN
        "order ahead" -> OpenState.ORDER_AHED
        else -> OpenState.CLOSED
    }
}

enum class OpenState {
    OPEN, ORDER_AHED, CLOSED
}