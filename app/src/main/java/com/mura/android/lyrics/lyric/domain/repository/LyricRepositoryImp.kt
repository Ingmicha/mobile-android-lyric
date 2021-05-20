package com.mura.android.lyrics.lyric.domain.repository

import com.google.gson.JsonObject
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.utils.remote.ResultManager
import retrofit2.Response

interface LyricRepositoryImp {

    suspend fun getLyric(artist: String, title: String): ResultManager<Response<JsonObject>>
    suspend fun getLyricsFromDB(): ResultManager<List<Lyric>>
    suspend fun insertLyric(lyric: Lyric): ResultManager<Long>

}