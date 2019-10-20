package com.giac.restauranttrends.view.ui.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.RestaurantItemLayoutBinding
import com.giac.restauranttrends.model.entity.Restaurant

class RestaurantListAdapter(private val callback : RestaurantItemCallback) : RecyclerView.Adapter<RestaurantListAdapter.RestaurantItemViewHolder>() {

    private var restaurantList: List<Restaurant> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.restaurant_item_layout, parent, false) as RestaurantItemLayoutBinding
        binding.root.setOnClickListener {
            binding.restaurant?.let {
                // TODO
            }
        }
        return RestaurantItemViewHolder(binding)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant)
    }

    fun setData(restaurantList: List<Restaurant>) {
        this.restaurantList = restaurantList
        notifyDataSetChanged()
    }

    inner class RestaurantItemViewHolder(var binding: RestaurantItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant : Restaurant) {
            binding.restaurant = restaurant
            binding.executePendingBindings()
        }
    }

}