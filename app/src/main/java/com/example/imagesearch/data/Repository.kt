package com.example.imagesearch.data

import com.example.imagesearch.network.RetrofitService

class Repository {

    suspend fun searchImages(apikey: String, query: String, sort: String, page: Int, size: Int) {
        return RetrofitService.getSearchImages(apikey, query, sort, page, size)
    }
}