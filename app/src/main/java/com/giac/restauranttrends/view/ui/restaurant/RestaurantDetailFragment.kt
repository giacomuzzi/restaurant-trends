package com.giac.restauranttrends.view.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.RestaurantDetailBinding
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.view.ui.AbstractBaseFragment

class RestaurantDetailFragment : AbstractBaseFragment() {

    private lateinit var binding : RestaurantDetailBinding
    private lateinit var restaurant : Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurant = requireArguments().getParcelable(RESTAURANT_EXTRA)!!
    }

    override fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.restaurant_detail, container, false)
        binding.restaurant = restaurant
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.reviewList.layoutManager = LinearLayoutManager(context)
        binding.reviewList.adapter = ReviewListAdapter(restaurant.reviewList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolbar()
    }

    companion object {

        private const val RESTAURANT_EXTRA = "restaurantExtra"

        fun newInstance(restaurant: Restaurant) : RestaurantDetailFragment {
            val fragment = RestaurantDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(RESTAURANT_EXTRA, restaurant)
            fragment.arguments = bundle
            return fragment
        }

    }

}