package com.burakcanduzcan.eterationnativedevelopmentstudycase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: String,
    val description: String
) : Parcelable