package com.giac.restauranttrends.view.ui.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.RestaurantListFragmentBinding
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.viewmodel.RestaurantListViewModel
import com.giac.restauranttrends.vo.Resource
import com.giac.restauranttrends.vo.Status

class RestaurantListFragment : Fragment() {

    private lateinit var binding : RestaurantListFragmentBinding
    private lateinit var restaurantListViewModel : RestaurantListViewModel
    private lateinit var restaurantListAdapter: RestaurantListAdapter

    override fun onCreateView(
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
                TODO("handle restaurant")
            }
        }
        )
        binding.restaurantList.layoutManager = LinearLayoutManager(context)
        binding.restaurantList.adapter = restaurantListAdapter

        restaurantListViewModel = ViewModelProviders.of(this).get(RestaurantListViewModel::class.java)
        // TODO hardcode
        restaurantListViewModel.getRestaurantOrderByRating("83", "1").observe(this, Observer { listResource ->
            handleResponse(listResource)
        })
    }

    // TODO mover codigo repetido a clase base
    private fun handleResponse(restaurantListResource: Resource<List<Restaurant>>?) {
        if (restaurantListResource != null) {
            when (restaurantListResource.status) {
                Status.ERROR -> {
                    // TODO agregar retry
                    binding.progressbar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = restaurantListResource.message
                }
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    if (restaurantListResource.data?.isNotEmpty() == true) {
                        restaurantListAdapter.setData(restaurantListResource.data)
                        restaurantListAdapter.notifyDataSetChanged()
                    } else {
                        binding.errorMessage.text = resources.getString(R.string.empty_collection_list_result)
                        binding.errorMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() : RestaurantListFragment =
            RestaurantListFragment()
    }
}