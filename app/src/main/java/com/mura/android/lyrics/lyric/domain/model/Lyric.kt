package com.mura.android.lyrics.lyric.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "lyric",
    indices = [Index(value = ["id"], unique = true)]
)
data class Lyric(

    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "artist")
    var artist: String,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "lyrics")
    @SerializedName("lyrics")
    val lyric: String
) {
    override fun toString(): String {
        return lyric
    }
}
