package com.giac.restauranttrends.view.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giac.restauranttrends.R
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.view.ui.AbstractBaseFragment

class RestaurantDetailFragment : AbstractBaseFragment() {

    private lateinit var restaurant : Restaurant

    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restaurant = requireArguments().getParcelable(RESTAURANT_EXTRA)!!
    }

    override fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.restaurant_detail, container, false)
        recyclerView = view.findViewById(R.id.content)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ReviewListAdapter(restaurant)
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