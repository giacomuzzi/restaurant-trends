package com.giac.restauranttrends.model.entity

import com.google.gson.annotations.SerializedName

data class City (
    val id : Int,
    val name : String,
    @SerializedName(value = "country_name")
    val countryName : String
)