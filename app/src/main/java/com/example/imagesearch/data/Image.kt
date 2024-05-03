package com.example.imagesearch.data

import com.example.imagesearch.network.RetrofitInstance.retrofitService
import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import com.example.imagesearch.data.ImageMeta as ImageMeta


data class Image(val response: ImageResponse)

data class ImageResponse(
    @SerializedName("meta")
    val ImageMeta: ImageMeta,
    @SerializedName("documents")
    val ImageDocuments: ImageDocuments
)

data class ImageMeta(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
)

data class ImageDocuments(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("display_sitename")
    val displaySiteName: String,
    @SerializedName("doc_url")
    val docUrl: String,
    @SerializedName("datetime")
    val dateTime: DateTime
)

val queryMap = hashMapOf(
    "query" to "검색어",
    "sort" to "accuracy", // 정확도 순으로 정렬
    "page" to "1", // 페이지 번호
    "size" to "80" // 한 페이지에 포함될 문서 수
)

val response = retrofitService.getImageMeta(queryMap)