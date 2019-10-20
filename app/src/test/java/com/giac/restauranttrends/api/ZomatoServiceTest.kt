package com.giac.restauranttrends.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.giac.restauranttrends.LiveDataTestUtil.getValue
import com.giac.restauranttrends.api.response.ApiSuccessResponse
import com.giac.restauranttrends.util.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ZomatoServiceTest {

    // swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ZomatoService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ZomatoService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCities() {
        enqueueResponse("cities.json")

        val apiSuccessResponse = getValue(service.getCities("-33.408204", "-70.543132")) as ApiSuccessResponse

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/cities?lat=-33.408204&lon=-70.543132"))

        val cities = apiSuccessResponse.body.locationSuggestions
        assertThat(cities.size, `is`(1))

        val santiagoCity = cities.first()
        assertThat(santiagoCity, notNullValue())
        assertThat(santiagoCity.id, `is`(83))
        assertThat(santiagoCity.countryName, `is`("Chile"))
        assertThat(santiagoCity.name, `is`("Santiago"))
    }

    @Test
    fun getCollections() {
        enqueueResponse("collections.json")

        val apiSuccessResponse = getValue(service.getCollections("83")) as ApiSuccessResponse

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/collections?city_id=83"))

        val collections = apiSuccessResponse.body.collections.map { it.collection }
        assertThat(collections.size, `is`(24))

        collections.forEach {
            assertThat(it, notNullValue())
        }

        val lastCollection = collections.last()
        assertThat(lastCollection.id, `is`(769))
        assertThat(lastCollection.title, `is`("Bread Day"))
        assertThat(lastCollection.description, `is`("For the best bread in the city, step right this way"))
        assertThat(lastCollection.imageUrl, notNullValue())
    }

    @Test
    fun getRestaurantOrderByRating() {
        enqueueResponse("restaurants.json")

        val apiSuccessResponse = getValue(service.getRestaurantOrderByRating("83", "1")) as ApiSuccessResponse

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/search?entity_type=city&sort=rating&order=desc&entity_id=83&collection_id=1"))

        val restaurants = apiSuccessResponse.body.restaurants.map { it.restaurant }
        assertThat(restaurants.size, `is`(30))

        restaurants.forEach {
            assertThat(it, notNullValue())
        }

        val lastRestaurant = restaurants.last()
        assertThat(lastRestaurant.id, `is`(8301532))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}