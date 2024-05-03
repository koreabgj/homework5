package com.example.imagesearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    const val API_KEY = "마이페이지 -> 인증키 발급 내역"

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
            .client(okHttpClient)    // Logcat에서 패킷 내용을 로그로 남기는 속성
            .baseUrl("https://openapi.gg.go.kr/")
            .build()
    }

    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}
