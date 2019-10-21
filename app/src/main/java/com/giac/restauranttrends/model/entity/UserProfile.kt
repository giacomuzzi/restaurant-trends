package com.giac.restauranttrends.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    val name : String,
    @SerializedName("profile_image")
    val profileImage : String
) : Parcelable