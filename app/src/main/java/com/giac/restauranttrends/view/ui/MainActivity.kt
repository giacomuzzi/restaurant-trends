package com.giac.restauranttrends.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giac.restauranttrends.R
import com.giac.restauranttrends.view.ui.common.NavigationController

class MainActivity : AppCompatActivity() {

    private val navigationController = NavigationController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigationController.navigateToCollectionListFragment()
        }
    }

}
