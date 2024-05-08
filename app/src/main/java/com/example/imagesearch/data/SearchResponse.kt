package com.example.imagesearch.data

import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import com.example.imagesearch.data.ImageMeta as ImageMeta

data class SearchResponse(
    @SerializedName("meta")
    val imageMeta: ImageMeta,
    @SerializedName("documents")
    val imageDocuments: ImageDocuments
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
    @SerializedName("thumbnail_url") //
    val thumbnailUrl: String,
    @SerializedName("image_url") //
    val imageUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("display_sitename") //
    val displaySiteName: String,
    @SerializedName("doc_url")
    val docUrl: String,
    @SerializedName("datetime") //
    val dateTime: DateTime
)