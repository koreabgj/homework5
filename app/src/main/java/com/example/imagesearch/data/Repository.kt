package com.example.imagesearch.data

import com.example.imagesearch.network.RetrofitClient

class Repository {
    suspend fun searchImages(
        authorization: String,
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): SearchResponse? {
        return try {
            val response = RetrofitClient.RetrofitInstance.retrofitService.getSearchImages(
                authorization = "KakaoAK d7dad5f8832c904973babb0a21d079ab",
                query = "검색어",
                sort = "accuracy",
                page = 1,
                size = 80
            )
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }
}