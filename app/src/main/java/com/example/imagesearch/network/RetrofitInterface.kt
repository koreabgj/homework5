package com.example.imagesearch.network

import com.example.imagesearch.data.Image
import com.google.rpc.context.AttributeContext
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("https://dapi.kakao.com") // 실제 엔드포인트
    suspend fun getImageMeta(@QueryMap param: HashMap<String, String>): Image
}

interface SearchService {
    @GET("https://dapi.kakao.com/search")
    suspend fun searchImages(@Query("query") query: String, @Query("apikey") apiKey: String): AttributeContext.Response
}