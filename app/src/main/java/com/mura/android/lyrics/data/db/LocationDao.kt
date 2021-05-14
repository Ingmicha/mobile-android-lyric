package com.mura.android.lyrics.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mura.android.lyrics.data.model.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: Location): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<Location>): List<Long>

}