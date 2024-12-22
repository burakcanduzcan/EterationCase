package com.burakcanduzcan.eterationnativedevelopmentstudycase.di

import android.content.Context
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.remote.MockApiService
import com.burakcanduzcan.eterationnativedevelopmentstudycase.util.Helper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, 10 * 1024 * 1024)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (Helper.hasNetwork(context)) {
                    request.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 60)
                        .build()
                } else {
                    request.newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        )
                        .build()
                }
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideMockApiService(client: OkHttpClient): MockApiService {
        return Retrofit.Builder()
            .baseUrl("https://5fc9346b2af77700165ae514.mockapi.io")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MockApiService::class.java)
    }
}