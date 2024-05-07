package com.example.imagesearch.network

import SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitService {
    interface RetrofitService {
        @GET("search/image")
        suspend fun getSearchImages(
            @Query("Authorization") authorization: String,
            @Query("query") query: String,
            @Query("sort") sort: String,
            @Query("page") page: Int,
            @Query("size") size: Int,
        ): SearchResponse

        fun getSearchImages(authorization: String): SearchResponse
    }

    // RetrofitService의 싱글톤 인스턴스
    private var instance: RetrofitService? = null

    fun getInstance(): RetrofitService {
        if (instance == null) {
            // instance가 null인 경우에만 인스턴스를 생성
            instance
        }
        return instance!!
    }

    fun getSearchImages(authorization: String, query: String, sort: String, page: Int, size: Int) {}
}