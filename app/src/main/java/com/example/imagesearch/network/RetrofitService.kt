package com.example.imagesearch.network

import SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("v2/search/image")
    suspend fun getSearchImages(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        authorization: String,
    ): Response<SearchResponse>
}