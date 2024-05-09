package com.example.imagesearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient {
    object RetrofitInstance {
        private const val BASE_URL = "https://dapi.kakao.com"

        private val okHttpClient: OkHttpClient by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()
        }

        val retrofitService: RetrofitService by lazy {
            retrofit.create(RetrofitService::class.java)
        }
    }
}