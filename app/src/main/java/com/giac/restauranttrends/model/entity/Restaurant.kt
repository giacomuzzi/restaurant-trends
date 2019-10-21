package com.giac.restauranttrends.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant (
    val id : Int,
    val name : String,
    val location : Location,
    @SerializedName("user_rating")
    val userRating : UserRating,
    val cuisines : String,
    val thumb : String,
    val timings : String,
    @SerializedName("average_cost_for_two")
    private val averageCoastForTwo : Int,
    val currency : String,
    @SerializedName("all_reviews")
    private val reviews : Reviews,
    @SerializedName("featured_image")
    val featuredImage : String
) : Parcelable {

    val averageCoastWithCurrency
        get() = currency + averageCoastForTwo / 2

    val reviewList
        get() = reviews.reviewList.map { it.review }

}