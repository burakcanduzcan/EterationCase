package com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BasketProductDao {
    @Query("SELECT * FROM BasketProductEntity")
    suspend fun getAll(): List<BasketProductEntity>

    @Insert
    suspend fun insert(basketProductEntity: BasketProductEntity)

    @Update
    suspend fun update(basketProductEntity: BasketProductEntity)

    @Delete
    suspend fun delete(basketProductEntity: BasketProductEntity)
}