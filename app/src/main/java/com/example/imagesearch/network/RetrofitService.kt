package com.example.imagesearch.network

import com.example.imagesearch.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("v2/search/image")
    suspend fun getSearchImages(
        @Header("Authorization") Authorization: String = "KakaoAK d7dad5f8832c904973babb0a21d079ab",
        @Query("query") query: String = "검색어",
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80,
    ): Response<SearchResponse>
}