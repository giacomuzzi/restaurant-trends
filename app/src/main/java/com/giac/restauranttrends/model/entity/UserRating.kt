package com.giac.restauranttrends.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRating(
    @SerializedName("aggregate_rating")
    val rating : Float,
    @SerializedName("rating_color")
    val color : String,
    val votes : String
) : Parcelable