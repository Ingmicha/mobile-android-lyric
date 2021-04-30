package com.mura.android.lyrics.data.repository

import com.google.gson.JsonObject
import com.mura.android.lyrics.data.remote.ApiServiceDataSource
import retrofit2.Response
import javax.inject.Inject

class LyricRepository @Inject constructor(
    private val apiServiceDataSource: ApiServiceDataSource
) {
    suspend fun findLyricByArtistAndTitle(artist:String, title:String): Response<JsonObject> {
        return apiServiceDataSource.getLyricByArtistAndTitle(artist,title)
    }

}