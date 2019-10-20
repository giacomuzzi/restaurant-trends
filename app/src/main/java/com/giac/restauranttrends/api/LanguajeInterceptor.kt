package com.giac.restauranttrends.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// TODO agregar soporte para localizacion
class LanguajeInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("lang", "es")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}
