package com.giac.restauranttrends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giac.restauranttrends.model.entity.City
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.repository.ZomatoRepository
import com.giac.restauranttrends.vo.Resource

class CollectionListViewModel : ViewModel() {

    private lateinit var citiesLiveData : LiveData<Resource<List<City>>>
    private lateinit var collectionLiveData : LiveData<Resource<List<Collection>>>

    fun getCities(lat : String, lon : String): LiveData<Resource<List<City>>> {
        if (!::citiesLiveData.isInitialized) {
            citiesLiveData = ZomatoRepository.getCities(lat, lon)
        }
        return citiesLiveData
    }

    fun getCollections(cityId : String): LiveData<Resource<List<Collection>>> {
        if (!::collectionLiveData.isInitialized) {
            collectionLiveData = ZomatoRepository.getCollections(cityId)
        }
        return collectionLiveData
    }

}