package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BasketProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun basketProductDao(): BasketProductDao
}