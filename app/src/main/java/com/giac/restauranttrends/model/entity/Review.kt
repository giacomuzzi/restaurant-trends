package com.giac.restauranttrends.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review (
    val rating : Float,
    @SerializedName("review_text")
    val text : String,
    @SerializedName("review_time_friendly")
    val timeFriendly : String,
    val user : UserProfile
) : Parcelable