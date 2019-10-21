package com.giac.restauranttrends.view.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.RestaurantListFragmentBinding
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.view.ui.AbstractBaseFragment
import com.giac.restauranttrends.view.ui.common.DefaultResponseHandler
import com.giac.restauranttrends.viewmodel.RestaurantListViewModel
import com.giac.restauranttrends.vo.Resource

class RestaurantListFragment : AbstractBaseFragment() {

    private lateinit var binding : RestaurantListFragmentBinding
    private lateinit var restaurantListViewModel : RestaurantListViewModel
    private lateinit var restaurantListAdapter: RestaurantListAdapter

    private lateinit var restaurantItemCallback: RestaurantItemCallback

    override fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.restaurant_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        restaurantListAdapter = RestaurantListAdapter(object : RestaurantItemCallback {
            override fun onClick(restaurant: Restaurant) {
                restaurantItemCallback.onClick(restaurant)
            }
        })
        binding.restaurantList.layoutManager = LinearLayoutManager(context)
        binding.restaurantList.adapter = restaurantListAdapter

        restaurantListViewModel = ViewModelProviders.of(this).get(RestaurantListViewModel::class.java)
        // TODO hardcode
        restaurantListViewModel.getRestaurantOrderByRating("83", "1").observe(this, RestaurantListResponseHandler())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.restaurant_list_title)
    }

    private inner class RestaurantListResponseHandler : DefaultResponseHandler<Resource<List<Restaurant>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@RestaurantListFragment

        override fun onSuccess(resource: Resource<List<Restaurant>>?) {
            super.onSuccess(resource)
            if (resource?.data?.isNotEmpty() == true) {
                restaurantListAdapter.setData(resource.data)
                restaurantListAdapter.notifyDataSetChanged()
            } else {
                errorMessage.text = resources.getString(R.string.empty_collection_list_result)
                errorMessage.visibility = View.VISIBLE
            }
        }
    }

    fun setRestaurantItemCallback(restaurantItemCallback: RestaurantItemCallback) {
        this.restaurantItemCallback = restaurantItemCallback
    }

    companion object {
        fun newInstance() : RestaurantListFragment =
            RestaurantListFragment()
    }
}