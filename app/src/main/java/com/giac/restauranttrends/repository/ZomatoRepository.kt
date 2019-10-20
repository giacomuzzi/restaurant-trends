package com.giac.restauranttrends.repository

import androidx.lifecycle.LiveData
import com.giac.restauranttrends.api.NetworkBoundResource
import com.giac.restauranttrends.api.NetworkModule
import com.giac.restauranttrends.api.ZomatoService
import com.giac.restauranttrends.api.response.ApiResponse
import com.giac.restauranttrends.api.response.CitiesResult
import com.giac.restauranttrends.api.response.CollectionsResult
import com.giac.restauranttrends.api.response.SearchResult
import com.giac.restauranttrends.model.entity.City
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.model.entity.Restaurant
import com.giac.restauranttrends.vo.Resource

object ZomatoRepository {

    val zomatoService: ZomatoService = NetworkModule.getRetrofit().create(ZomatoService::class.java)

    // TODO agregar soporte para guardar cities en cache ya que no tiene sentido hacer un request cada vez que se necesiten los datos
    fun getCities(lat : String, lon : String): LiveData<Resource<List<City>>> {
        return object : NetworkBoundResource<List<City>, CitiesResult>() {

            override fun createCall(): LiveData<ApiResponse<CitiesResult>> =
                zomatoService.getCities(lat, lon)

            override fun processResponse(result: CitiesResult): List<City> =
                result.locationSuggestions

        }.asLiveData()
    }

    fun getCollections(cityId : String) : LiveData<Resource<List<Collection>>> {
        return object : NetworkBoundResource<List<Collection>, CollectionsResult>() {

            override fun createCall(): LiveData<ApiResponse<CollectionsResult>> =
                zomatoService.getCollections(cityId)

            override fun processResponse(result: CollectionsResult): List<Collection> =
                result.collections.map { it.collection }

        }.asLiveData()
    }

    fun getRestaurantOrderByRating(cityId : String, collectionId : String) : LiveData<Resource<List<Restaurant>>> {
        return object : NetworkBoundResource<List<Restaurant>, SearchResult>() {

            override fun createCall(): LiveData<ApiResponse<SearchResult>> =
                zomatoService.getRestaurantOrderByRating(cityId, collectionId)

            override fun processResponse(result: SearchResult): List<Restaurant> =
                result.restaurants.map { it.restaurant }

        }.asLiveData()
    }
}