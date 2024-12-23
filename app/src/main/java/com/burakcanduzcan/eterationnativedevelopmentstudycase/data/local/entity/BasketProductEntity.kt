package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BasketProductEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val price: String,
    val basketQuantity: Int = 0
)