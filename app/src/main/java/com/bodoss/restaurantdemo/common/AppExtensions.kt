@file:JvmName("AppExtensions")

package com.bodoss.restaurantdemo.common

import android.graphics.Color

fun String?.statusColor(): Int {
    return when (this) {
        "open" -> Color.GREEN
        "order ahead" -> 0xFFF19C38.toInt()
        "closed" -> Color.RED
        else -> Color.BLACK
    }
}