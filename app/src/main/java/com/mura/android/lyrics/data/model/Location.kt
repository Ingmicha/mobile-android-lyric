package com.mura.android.lyrics.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)