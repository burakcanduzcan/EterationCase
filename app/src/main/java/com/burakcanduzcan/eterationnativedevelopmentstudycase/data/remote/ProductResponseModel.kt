package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponseModel(
    val createdAt: String,
    val name: String,
    val image: String,
    val price: String,
    val description: String,
    val model: String,
    val brand: String,
    val id: String
)