package com.example.imagesearch.data

import com.example.imagesearch.network.RetrofitInstance

class Repository {
    suspend fun searchImages(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): List<ImageDocuments> {
        return try {
            val response = RetrofitInstance.retrofitService.getSearchImages(
                query = query,
                sort = sort,
                page = page,
                size = size
            )
            if (response.isSuccessful) {
                response.body()?.imageDocuments ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}