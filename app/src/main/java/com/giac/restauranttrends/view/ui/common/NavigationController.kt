package com.giac.restauranttrends.view.ui.common

import androidx.fragment.app.Fragment
import com.giac.restauranttrends.R
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.view.ui.MainActivity
import com.giac.restauranttrends.view.ui.collection.CollectionListFragment
import com.giac.restauranttrends.view.ui.restaurant.RestaurantDetailFragment
import com.giac.restauranttrends.view.ui.restaurant.RestaurantListFragment

class NavigationController(private val mainActivity : MainActivity) {

    fun navigateToCollectionListFragment() {
        val fragment = CollectionListFragment.newInstance()
        replaceFragment(fragment)
    }

    fun navigateToRestaurantListFragment(cityId : String, collectionId : String) {
        val fragment = RestaurantListFragment.newInstance(cityId, collectionId)
        replaceFragment(fragment)
    }

    fun navigateToRestaurantDetailFragment(restaurant: Restaurant) {
        val fragment = RestaurantDetailFragment.newInstance(restaurant)
        replaceFragment(fragment)
    }

    private fun replaceFragment(newFragment : Fragment) {
        mainActivity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
            .replace(R.id.container, newFragment)
            .addToBackStack(newFragment.javaClass.name)
            .commitAllowingStateLoss()
    }
}