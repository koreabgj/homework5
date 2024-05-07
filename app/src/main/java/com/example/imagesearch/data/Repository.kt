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
                authorization = "KakaoAK $apiKey",
                query = query,
                sort = sort,
                page = page,
                size = size
            )
        } catch (e: Exception) {
            throw e
        }
    }
}