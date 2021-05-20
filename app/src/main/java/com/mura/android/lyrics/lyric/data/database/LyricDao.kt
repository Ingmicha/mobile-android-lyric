package com.mura.android.lyrics.lyric.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.utils.remote.ResultManager

@Dao
interface LyricDao {
    @Query("SELECT * FROM lyric")
    suspend fun getAllLyrics(): ResultManager<List<Lyric>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lyric: Lyric): ResultManager<Long>
}