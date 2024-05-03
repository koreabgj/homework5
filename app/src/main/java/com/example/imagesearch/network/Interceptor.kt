package com.example.imagesearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // 로깅 레벨 설정
}

val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor) // 로깅 인터셉터 추가
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://dapi.kakao.com")
    .addConverterFactory(MoshiConverterFactory.create())
    .client(client) // OkHttpClient 설정
    .build()