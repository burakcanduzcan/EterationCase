package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.dao.BasketProductDao
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.dao.FavoriteProductDao
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.BasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.FavoriteProductEntity

@Database(entities = [BasketProductEntity::class, FavoriteProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketProductDao(): BasketProductDao
    abstract fun favoriteProductDao(): FavoriteProductDao
}