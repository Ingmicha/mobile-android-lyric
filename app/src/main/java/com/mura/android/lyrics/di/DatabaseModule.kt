package com.mura.android.lyrics.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mura.android.lyrics.data.db.AppDatabase
import com.mura.android.lyrics.data.db.LyricDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "db_lyric"
        ).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): LyricDao {
        return appDatabase.lyricDao()
    }

}