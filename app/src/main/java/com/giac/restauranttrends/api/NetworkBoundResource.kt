package com.giac.restauranttrends.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.giac.restauranttrends.api.response.ApiErrorResponse

import com.giac.restauranttrends.api.response.ApiResponse
import com.giac.restauranttrends.api.response.ApiSuccessResponse
import com.giac.restauranttrends.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        val apiResponse = createCall()
        result.value = Resource.loading(null)
        result.addSource(apiResponse) {
            when (it) {
                is ApiSuccessResponse -> {
                    result.value = Resource.success(processResponse(it.body))
                }
                is ApiErrorResponse -> {
                    result.value = Resource.error(it.errorMessage)
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun processResponse(result: RequestType): ResultType

}
