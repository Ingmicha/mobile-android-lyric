package com.mura.android.lyrics.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mura.android.lyrics.data.model.Lyric

@Database(entities = [Lyric::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lyricDao(): LyricDao
}