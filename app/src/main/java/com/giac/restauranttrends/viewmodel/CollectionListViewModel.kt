package com.giac.restauranttrends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giac.restauranttrends.model.entity.City
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.repository.ZomatoRepository
import com.giac.restauranttrends.vo.Resource

class CollectionListViewModel : ViewModel() {

    private lateinit var cityId : String
    private lateinit var collectionsLiveData : LiveData<Resource<List<Collection>>>

    // TODO optimizar si query es la actual
    fun findCities(query: String) : LiveData<Resource<List<City>>> {
        return ZomatoRepository.findCities(query)
    }

    fun getCollections(cityId : String): LiveData<Resource<List<Collection>>> {
        if (!this::cityId.isInitialized || cityId != this.cityId) {
            this.cityId = cityId
            collectionsLiveData = ZomatoRepository.getCollections(cityId)
        }
        return collectionsLiveData
    }

}