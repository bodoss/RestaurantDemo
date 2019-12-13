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
    val status: String? = null
)