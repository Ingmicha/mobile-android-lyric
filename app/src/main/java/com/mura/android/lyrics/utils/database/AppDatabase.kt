package com.mura.android.lyrics.utils.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mura.android.lyrics.location.data.database.LocationDao
import com.mura.android.lyrics.data.model.Location
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.lyric.data.database.LyricDao

@Database(entities = [Lyric::class, Location::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lyricDao(): LyricDao
    abstract fun locationDao(): LocationDao
}