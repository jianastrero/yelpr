package com.jianastrero.yelpr.singleton

import com.jianastrero.yelpr.BuildConfig
import com.jianastrero.yelpr.api.YelpApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {

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

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.yelp.com/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val yelpApi: YelpApi = retrofit.create(YelpApi::class.java)

    fun yelpApi() = yelpApi
}