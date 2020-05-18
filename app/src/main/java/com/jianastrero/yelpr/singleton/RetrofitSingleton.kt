package com.jianastrero.yelpr.singleton

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.yelp.com/v3")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create() =
        retrofit.create(T::class.java)
}