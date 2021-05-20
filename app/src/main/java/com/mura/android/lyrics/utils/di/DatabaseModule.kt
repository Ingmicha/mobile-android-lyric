package com.mura.android.lyrics.utils.di

import android.content.Context
import androidx.room.Room
import com.mura.android.lyrics.utils.database.AppDatabase
import com.mura.android.lyrics.location.data.database.LocationDao
import com.mura.android.lyrics.lyric.data.database.LyricDao
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
    fun provideLyricDao(appDatabase: AppDatabase): LyricDao {
        return appDatabase.lyricDao()
    }

    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }

}