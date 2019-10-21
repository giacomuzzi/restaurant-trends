package com.giac.restauranttrends.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.giac.restauranttrends.R
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.view.ui.collection.CollectionItemCallback
import com.giac.restauranttrends.view.ui.collection.CollectionListFragment
import com.giac.restauranttrends.view.ui.common.NavigationController
import com.giac.restauranttrends.view.ui.restaurant.RestaurantItemCallback
import com.giac.restauranttrends.view.ui.restaurant.RestaurantListFragment

class MainActivity : AppCompatActivity() {

    private val navigationController = NavigationController(this)

    private val collectionItemCallback = object : CollectionItemCallback {

        override fun onClick(collection: Collection) {
            navigationController.navigateToRestaurantListFragment()
        }
    }

    private val restaurantItemCallback = object : RestaurantItemCallback {

        override fun onClick(restaurant: Restaurant) {
            navigationController.navigateToRestaurantDetailFragment(restaurant)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigationController.navigateToCollectionListFragment()
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is CollectionListFragment) {
            fragment.setCollectionItemCallback(collectionItemCallback)
        }
        if (fragment is RestaurantListFragment) {
            fragment.setRestaurantItemCallback(restaurantItemCallback)
        }
    }

}
