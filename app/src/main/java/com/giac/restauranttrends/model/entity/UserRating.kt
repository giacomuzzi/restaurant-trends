package com.giac.restauranttrends.model.entity

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("aggregate_rating")
    val rating : Float,
    @SerializedName("rating_color")
    val color : String
)