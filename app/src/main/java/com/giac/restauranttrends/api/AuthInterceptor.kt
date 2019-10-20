package com.giac.restauranttrends.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("user-key", ZOMATO_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {

        private val ZOMATO_API_KEY = "61b8c97a32fae60fe9a28ed0f7b45d94"
    }
}
