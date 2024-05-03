package com.example.imagesearch.network

import com.example.imagesearch.data.Image
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {
    @GET("https://dapi.kakao.com") // 실제 엔드포인트
    suspend fun getImageData(@QueryMap param: HashMap<String, String>): Image
}