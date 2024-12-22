package com.burakcanduzcan.eterationnativedevelopmentstudycase.di

import android.content.Context
import androidx.room.Room
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "eteration_case_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideBasketProductDao(appDatabase: AppDatabase) = appDatabase.basketProductDao()
}