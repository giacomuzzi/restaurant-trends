package com.giac.restauranttrends.view.ui.common

import androidx.fragment.app.Fragment
import com.giac.restauranttrends.R
import com.giac.restauranttrends.view.ui.MainActivity
import com.giac.restauranttrends.view.ui.fragment.CollectionListFragment

class NavigationController(private val mainActivity : MainActivity) {

    fun navigateToCollectionListFragment() {
        replaceFragment(CollectionListFragment.newInstance())
    }

    private fun replaceFragment(newFragment : Fragment) {
        mainActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, newFragment)
            .commitAllowingStateLoss()
    }
}