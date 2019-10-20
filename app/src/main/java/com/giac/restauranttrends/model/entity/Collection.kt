package com.giac.restauranttrends.model.entity

import com.google.gson.annotations.SerializedName

data class Collection (
    @SerializedName(value = "collection_id")
    val id : Int,
    val title : String,
    @SerializedName(value = "image_url")
    val imageUrl : String,
    val description : String
)