package com.giac.restauranttrends.model.entity

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("locality_verbose")
    var localityVerbose : String
)