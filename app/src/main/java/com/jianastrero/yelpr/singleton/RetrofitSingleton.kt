package com.jianastrero.yelpr.singleton

import com.jianastrero.yelpr.BuildConfig
import com.jianastrero.yelpr.api.YelpApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitSingleton {

    private val yelpApi: YelpApi = create()

    private val client = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.MINUTES)
        .addInterceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.YELP_TOKEN}")
                    .build()
            )
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.yelp.com/v3")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    inline fun <reified T> create() =
        retrofit.create(T::class.java)

    fun yelpApi() = yelpApi
}