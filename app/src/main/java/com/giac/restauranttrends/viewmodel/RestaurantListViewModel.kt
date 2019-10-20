package com.giac.restauranttrends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.repository.ZomatoRepository
import com.giac.restauranttrends.vo.Resource

class RestaurantListViewModel : ViewModel() {

    private lateinit var restaurantsLiveData : LiveData<Resource<List<Restaurant>>>

    fun getRestaurantOrderByRating(cityId : String, collectionId : String) : LiveData<Resource<List<Restaurant>>> {
        if (!::restaurantsLiveData.isInitialized) {
            restaurantsLiveData = ZomatoRepository.getRestaurantOrderByRating(cityId, collectionId)
        }
        return restaurantsLiveData
    }

}