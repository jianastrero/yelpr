package com.jianastrero.yelpr.singleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jianastrero.yelpr.BuildConfig
import com.jianastrero.yelpr.api.YelpApi
import com.jianastrero.yelpr.singleton.RetrofitSingleton.gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.MINUTES)
        .addInterceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .header("Authorization", BuildConfig.YELP_TOKEN)
                    .build()
            )
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val gson: Gson = GsonBuilder()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.yelp.com/v3/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    private val yelpApi: YelpApi = retrofit.create(YelpApi::class.java)

    fun yelpApi() = yelpApi
}

fun Any.toJson() = gson.toJson(this)

inline fun <reified T> String.fromJson() = gson.fromJson(this, T::class.java)