package com.example.imagesearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class searchModel(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int
) : Parcelable