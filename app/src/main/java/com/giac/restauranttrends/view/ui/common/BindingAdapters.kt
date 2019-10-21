package com.giac.restauranttrends.view.ui.common

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.giac.restauranttrends.model.entity.Review
import com.giac.restauranttrends.model.entity.UserRating



object BindingAdapters {

    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    fun setImageUrl(view : ImageView, url : String?) {
        // TODO add error image
        Glide.with(view.context)
            .load(url)
            .dontAnimate()
            .into(view)
    }

    @BindingAdapter("bind:userRating")
    @JvmStatic
    fun setRating(text : TextView, userRating : UserRating) {
        setRating(text, userRating.rating.toString(), userRating.color)

    }

    @BindingAdapter("bind:userRating")
    @JvmStatic
    fun setRating(text : TextView, review : Review) {
        setRating(text, review.rating.toString(), review.ratingColor)
    }

    // TODO move to ColorUtils
    private fun setRating(text : TextView, rating : String, ratingColor : String) {
        text.text = rating.toString()

        // TODO move to ColorUtils
        val color = Color.parseColor("#$ratingColor")
        text.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
