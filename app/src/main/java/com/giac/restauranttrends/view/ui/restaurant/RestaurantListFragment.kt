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
import com.giac.restauranttrends.util.requireData
import com.giac.restauranttrends.view.ui.AbstractBaseFragment
import com.giac.restauranttrends.view.ui.common.DefaultResponseHandler
import com.giac.restauranttrends.viewmodel.RestaurantListViewModel
import com.giac.restauranttrends.vo.Resource

class RestaurantListFragment : AbstractBaseFragment() {

    private lateinit var binding : RestaurantListFragmentBinding
    private lateinit var restaurantListViewModel : RestaurantListViewModel
    private lateinit var restaurantListAdapter: RestaurantListAdapter

    private lateinit var restaurantItemCallback: RestaurantItemCallback

    private lateinit var cityId: String
    private lateinit var collectionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityId = requireArguments().getString(CITY_ID_EXTRA)!!
        collectionId = requireArguments().getString(COLLECTION_ID_EXTRA)!!
    }

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
        restaurantListViewModel.getRestaurantOrderByRating(cityId, collectionId).observe(this, RestaurantListResponseHandler())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.restaurant_list_title)
    }

    private inner class RestaurantListResponseHandler : DefaultResponseHandler<Resource<List<Restaurant>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@RestaurantListFragment

        override fun onSuccess(resource: Resource<List<Restaurant>>?) {
            restaurantListAdapter.setData(resource!!.requireData())
        }
    }

    fun setRestaurantItemCallback(restaurantItemCallback: RestaurantItemCallback) {
        this.restaurantItemCallback = restaurantItemCallback
    }

    companion object {

        private const val CITY_ID_EXTRA = "cityIdExtra"
        private const val COLLECTION_ID_EXTRA = "collectionIdExtra"

        fun newInstance(cityId : String, collectionId : String) : RestaurantListFragment {
            val fragment = RestaurantListFragment()
            val bundle = Bundle()
            bundle.putString(CITY_ID_EXTRA, cityId)
            bundle.putString(COLLECTION_ID_EXTRA, collectionId)
            fragment.arguments = bundle
            return fragment
        }

    }
}