package com.example.imagesearch.data

import com.squareup.moshi.Json
import java.util.Date

data class SearchResponse(
    @Json(name ="meta")
    val imageMeta: ImageMeta,
    @Json(name ="documents")
    val imageDocuments: List<ImageDocuments>
)

data class ImageMeta(
    @Json(name="total_count")
    val totalCount: Int,
    @Json(name="pageable_count")
    val pageableCount: Int,
    @Json(name="is_end")
    val isEnd: Boolean
)

data class ImageDocuments(
    @Json(name="collection")
    val collection: String,
    @Json(name="thumbnail_url") //
    val thumbnailUrl: String,
    @Json(name="image_url")
    val imageUrl: String,
    @Json(name="width")
    val width: Int,
    @Json(name="height")
    val height: Int,
    @Json(name="display_sitename") //
    val displaySiteName: String,
    @Json(name="doc_url")
    val docUrl: String,
    @Json(name="datetime") //
    val dateTime: Date
)