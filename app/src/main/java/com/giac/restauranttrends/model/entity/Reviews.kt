package com.giac.restauranttrends.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reviews(
    @SerializedName("reviews")
    val reviewList : List<ReviewWrapper>
) : Parcelable

@Parcelize
data class ReviewWrapper(
    val review : Review
) : Parcelable