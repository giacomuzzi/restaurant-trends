package com.giac.restauranttrends.api.response

import com.giac.restauranttrends.model.entity.Restaurant

data class SearchResult(
    val restaurants : List<RestaurantWrapper>
)

data class RestaurantWrapper (
    val restaurant: Restaurant
)