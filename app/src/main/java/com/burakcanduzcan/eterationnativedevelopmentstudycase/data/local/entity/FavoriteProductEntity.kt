package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val price: String,
    val description: String
)