package com.giac.restauranttrends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.giac.restauranttrends.model.entity.City
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.repository.ZomatoRepository
import com.giac.restauranttrends.vo.Resource

class CollectionListViewModel : ViewModel() {

    fun getCities(lat : String, lon : String): LiveData<Resource<List<City>>> {
        // TODO guardar LiveData para no generar instancias de gusto, mantener dentro del estado del vm
        return ZomatoRepository.getCities(lat, lon)
    }

    fun getCollections(cityId : String): LiveData<Resource<List<Collection>>> {
        // TODO guardar LiveData para no generar instancias de gusto, mantener dentro del estado del vm
        return ZomatoRepository.getCollections(cityId)
    }

}