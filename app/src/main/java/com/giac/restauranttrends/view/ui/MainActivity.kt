package com.giac.restauranttrends.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.giac.restauranttrends.R
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.viewmodel.CollectionListViewModel
import com.giac.restauranttrends.vo.Resource
import com.giac.restauranttrends.vo.Status

class MainActivity : AppCompatActivity() {

    private lateinit var collectionListViewModel : CollectionListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collectionListViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        // TODO hardcode
//        collectionListViewModel.getCities("-33.408204", "-70.543132").observe(this, Observer { citiesResource ->
//            handleResponse(citiesResource)
//        })

//        collectionListViewModel.getCollections("83").observe(this, Observer { citiesResource ->
//            handleResponse(citiesResource)
//        })

        collectionListViewModel.getRestaurantOrderByRating("83", "1").observe(this, Observer { citiesResource ->
            handleResponse(citiesResource)
        })
    }

    private fun handleResponse(citiesResource: Resource<List<Restaurant>>?) {
        if (citiesResource != null) {
            when (citiesResource.status) {
                Status.ERROR -> {
                    // TODO
                }
                Status.LOADING -> {
                    // TODO
                }
                Status.SUCCESS -> {
                    // TODO
                }
            }
        }
    }
}
