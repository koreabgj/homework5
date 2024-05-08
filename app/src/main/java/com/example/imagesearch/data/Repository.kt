package com.example.imagesearch.data

import com.example.imagesearch.network.RetrofitClient.RetrofitInstance.retrofitService
import retrofit2.Response

class Repository {
    suspend fun searchImages(
        apiKey: String,
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<SearchResponse> {
        try {
            return retrofitService.getSearchImages(
                authorization = "KakaoAK d7dad5f8832c904973babb0a21d079ab",
                query = "검색어",
                sort = "accuracy",
                page = 1,
                size = 80
            )
        } catch (e: Exception) {
            throw e
        }
    }
}