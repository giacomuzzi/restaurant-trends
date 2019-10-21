package com.giac.restauranttrends.view.ui.common

import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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
        text.text =  userRating.rating.toString()

        // TODO move to ColorUtils
        val color = Color.parseColor("#" + userRating.color)
        text.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
