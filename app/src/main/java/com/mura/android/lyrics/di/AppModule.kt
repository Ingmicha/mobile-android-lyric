package com.mura.android.lyrics.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mura.android.lyrics.data.remote.ApiServiceDataSource
import com.mura.android.lyrics.data.remote.ApiServiceDataSourceImp
import com.mura.android.lyrics.data.remote.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBseURL() = "https://api.lyrics.ovh/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        if (true) { // debug ON
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            return OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BaseURL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    fun provideApiServices(retrofit: Retrofit) =
        retrofit.create(ApiServices::class.java)

    @Provides
    fun provideApiServiceHelper(apiHelper: ApiServiceDataSource):
            ApiServiceDataSourceImp = apiHelper

}