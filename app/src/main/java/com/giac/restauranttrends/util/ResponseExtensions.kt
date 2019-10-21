package com.giac.restauranttrends.util

import com.giac.restauranttrends.vo.Resource

fun <T> Resource<T>.requireData(): T {
    return data!!
}