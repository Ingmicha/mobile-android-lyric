package com.mura.android.lyrics.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mura.android.lyrics.data.model.Lyric

@Dao
interface LyricDao {
    @Query("SELECT * FROM lyric")
    suspend fun getAllLyrics(): List<Lyric>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lyric: Lyric)
}