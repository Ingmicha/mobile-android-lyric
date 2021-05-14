package com.mura.android.lyrics.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mura.android.lyrics.data.model.Location
import com.mura.android.lyrics.data.model.Lyric

@Database(entities = [Lyric::class, Location::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lyricDao(): LyricDao
    abstract fun locationDao(): LocationDao
}