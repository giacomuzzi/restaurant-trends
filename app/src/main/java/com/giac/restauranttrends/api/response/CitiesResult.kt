package com.giac.restauranttrends.api.response

import com.giac.restauranttrends.model.entity.City
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class CitiesResult (
    @SerializedName(value = "location_suggestions")
    val locationSuggestions: List<City>
)