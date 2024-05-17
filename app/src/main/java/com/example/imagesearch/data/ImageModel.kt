package com.example.imagesearch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
) : Parcelable