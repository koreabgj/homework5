package com.example.imagesearch.network

import SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search/image")
    suspend fun getSearchImages(
        @Query("Authorization") Authorization: HashMap<String, String>,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchResponse>
}