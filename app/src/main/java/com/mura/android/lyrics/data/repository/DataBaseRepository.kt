package com.mura.android.lyrics.data.repository

import com.mura.android.lyrics.data.db.LyricDao
import com.mura.android.lyrics.data.model.Lyric
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val lyricDao: LyricDao
) {
    suspend fun insetLyric(lyric: Lyric) = lyricDao.insert(lyric)

    suspend fun getAllLyrics() = lyricDao.getAllLyrics()

}