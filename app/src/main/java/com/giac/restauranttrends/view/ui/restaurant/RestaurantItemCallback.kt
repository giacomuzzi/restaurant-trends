package com.giac.restauranttrends.view.ui.restaurant

import com.giac.restauranttrends.model.entity.Restaurant

interface RestaurantItemCallback {
    fun onClick(restaurant: Restaurant)
}