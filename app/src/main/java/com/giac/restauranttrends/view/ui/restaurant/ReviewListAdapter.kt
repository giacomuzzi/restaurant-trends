package com.giac.restauranttrends.view.ui.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.ReviewItemLayoutBinding
import com.giac.restauranttrends.model.entity.Review

// TODO refactor: mover codigo repetido entre adapters a una clase base
class ReviewListAdapter(
    private var reviewList: List<Review>
) : RecyclerView.Adapter<ReviewListAdapter.ReviewItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.review_item_layout, parent, false) as ReviewItemLayoutBinding
        return ReviewItemViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        val review = reviewList[position]
        holder.bind(review)
    }

    inner class ReviewItemViewHolder(var binding: ReviewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review : Review) {
            binding.review = review
            binding.executePendingBindings()
        }
    }

}