package com.giac.restauranttrends.model.entity

import com.google.gson.annotations.SerializedName

data class Restaurant (
    val id : Int,
    val name : String,
    val location : Location,
    @SerializedName("user_rating")
    val userRating : UserRating,
    val cuisines : String,
    val thumb : String
)