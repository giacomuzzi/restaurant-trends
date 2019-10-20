package com.giac.restauranttrends.api


import com.giac.restauranttrends.BuildConfig
import com.giac.restauranttrends.util.AppConstants
import com.giac.restauranttrends.util.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is responsible for setting up Retrofit and anything related to network calls.
 *
 */

object NetworkModule {

    private lateinit var retrofit: Retrofit

    fun getRetrofit(): Retrofit {
        if (!this::retrofit.isInitialized) {
            val httpLoggingInterceptor = provideOkHttpInterceptors()
            val okHttpClient = okHttpClient(httpLoggingInterceptor)
            retrofit = provideRetrofitClient(okHttpClient)
        }
        return retrofit
    }

    fun provideOkHttpInterceptors(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }


    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(LanguajeInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(AppConstants.CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(AppConstants.WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(AppConstants.READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Serialize Objects
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }
}
