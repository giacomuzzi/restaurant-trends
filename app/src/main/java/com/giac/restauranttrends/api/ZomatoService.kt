package com.giac.restauranttrends.api

import androidx.lifecycle.LiveData
import com.giac.restauranttrends.api.response.ApiResponse
import com.giac.restauranttrends.api.response.CitiesResult
import com.giac.restauranttrends.api.response.CollectionsResult
import com.giac.restauranttrends.api.response.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ZomatoService {

    @GET("cities")
    fun getCities(@Query("lat") lat : String,
                  @Query("lon") lon : String): LiveData<ApiResponse<CitiesResult>>

    @GET("collections")
    fun getCollections(@Query("city_id") cityId : String) : LiveData<ApiResponse<CollectionsResult>>

    // TODO analizar si hace falta paginar datos
    @GET("search?entity_type=city&sort=rating&order=desc")
    fun getRestaurantOrderByRating(@Query("entity_id") entityId : String,
                                   @Query("collection_id") collectionId : String) : LiveData<ApiResponse<SearchResult>>
}