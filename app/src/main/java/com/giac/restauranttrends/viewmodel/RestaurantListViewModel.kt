package com.giac.restauranttrends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.repository.ZomatoRepository
import com.giac.restauranttrends.vo.Resource

class RestaurantListViewModel : ViewModel() {

    fun getRestaurantOrderByRating(cityId : String, collectionId : String) : LiveData<Resource<List<Restaurant>>> {
        // TODO guardar LiveData para no generar instancias de gusto, mantener dentro del estado del vm
        return ZomatoRepository.getRestaurantOrderByRating(cityId, collectionId)
    }

}