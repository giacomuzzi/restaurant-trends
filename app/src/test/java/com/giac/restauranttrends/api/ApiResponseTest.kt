package com.giac.restauranttrends.api

import com.giac.restauranttrends.api.response.ApiErrorResponse
import com.giac.restauranttrends.api.response.ApiResponse
import com.giac.restauranttrends.api.response.ApiSuccessResponse
import okhttp3.MediaType
import okhttp3.ResponseBody

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat(errorMessage, `is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse.create<String>(Response.success("foo")) as ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is`("foo"))
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            ResponseBody.create(MediaType.parse("application/txt"), "blah")
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("blah"))
    }
}
