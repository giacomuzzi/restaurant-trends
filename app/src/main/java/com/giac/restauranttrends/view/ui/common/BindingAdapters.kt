package com.giac.restauranttrends.view.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    fun setImageUrl(view : ImageView, url : String?) {
        Glide.with(view.context)
            .load(url)
            .dontAnimate()
            .into(view)
    }

}
