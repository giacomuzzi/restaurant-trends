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

        val apiSuccessResponse = getValue(service.findCities("Santiago")) as ApiSuccessResponse

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/cities?q=Santiago"))

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

        val collections = apiSuccessResponse.body.collections!!.map { it.collection }
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
        assertThat(lastRestaurant.name, `is`("Al Jazeera"))
        assertThat(lastRestaurant.cuisines, `is`("Arabian"))
        assertThat(lastRestaurant.thumb, `is`("https://b.zmtcdn.com/data/reviews_photos/a02/ec58c0b4cf2bb30629b0239498a7ea02_1529091046.jpg?fit=around%7C200%3A200&crop=200%3A200%3B%2A%2C%2A"))
        assertThat(lastRestaurant.timings, `is`("11 AM to 8 PM (Mon-Fri),Closed (Sat-Sun)"))

        // coast
        assertThat(lastRestaurant.currency, `is`("$"))
        assertThat(lastRestaurant.averageCoastWithCurrency, `is`("$6000"))

        // user rating
        assertThat(lastRestaurant.userRating.rating, `is`(3.8F))
        assertThat(lastRestaurant.userRating.color, `is`("9ACD32"))

        // location
        assertThat(lastRestaurant.location.localityVerbose, `is`("Galería Edificio Huérfanos 1373, Santiago Centro, Santiago"))

        // reviews
        val firstReview = lastRestaurant.reviewList[0]
        assertThat(lastRestaurant.reviewList.size, `is`(5))
        assertThat(firstReview.rating, `is`(3F))
        assertThat(firstReview.text, notNullValue())
        assertThat(firstReview.timeFriendly, `is`("may 13, 2018"))

        // user profile
        val userProfile = firstReview.user
        assertThat(userProfile, notNullValue())
        assertThat(userProfile.name, `is`("Gabriel Rodriguez"))
        assertThat(userProfile.profileImage, `is`("https://b.zmtcdn.com/data/user_profile_pictures/98e/a89b9ff7edd16021c11a14978fb6698e.jpg?fit=around%7C100%3A100&crop=100%3A100%3B%2A%2C%2A"))

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