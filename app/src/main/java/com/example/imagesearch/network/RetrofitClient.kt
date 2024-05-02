package com.example.imagesearch.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.sql.DataSource

object RetrofitClient {

    private const val BASE_URL = " "

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dataSource: DataSource by lazy {
        retrofit.create(DataSource::class.java)
    }
}