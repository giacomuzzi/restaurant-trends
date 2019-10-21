package com.giac.restauranttrends.view.ui.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.giac.restauranttrends.databinding.RestaurantHeaderLayoutBinding
import com.giac.restauranttrends.databinding.ReviewItemLayoutBinding
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.model.entity.Review

// TODO refactor: mover codigo repetido entre adapters a una clase base
class ReviewListAdapter(
    restaurant: Restaurant
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var objects : MutableList<Any> = mutableListOf()

    init {
        objects.add(restaurant)
        objects.addAll(restaurant.reviewList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), com.giac.restauranttrends.R.layout.restaurant_header_layout, parent, false) as RestaurantHeaderLayoutBinding
                RestaurantHeaderViewHolder(binding)
            }
            TYPE_REVIEW -> {
                val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), com.giac.restauranttrends.R.layout.review_item_layout, parent, false) as ReviewItemLayoutBinding
                ReviewItemViewHolder(binding)
            }
            else -> throw RuntimeException("unexpected error")
        }
    }

    override fun getItemCount(): Int = objects.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            val restaurant = objects[position] as Restaurant
            (holder as RestaurantHeaderViewHolder).bind(restaurant)
        } else {
            val review = objects[position] as Review
            (holder as ReviewItemViewHolder).bind(review)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_REVIEW
    }

    inner class RestaurantHeaderViewHolder(var binding: RestaurantHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant : Restaurant) {
            binding.restaurant = restaurant
            binding.executePendingBindings()
        }
    }

    inner class ReviewItemViewHolder(var binding: ReviewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review : Review) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_REVIEW = 1
    }

}