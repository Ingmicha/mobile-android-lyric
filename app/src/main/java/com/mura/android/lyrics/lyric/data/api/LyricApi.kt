package com.mura.android.lyrics.lyric.data.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LyricApi {

    @GET("{artist}/{title}")
    suspend fun getLyricByArtistAndTitle(
        @Path(value = "artist", encoded = true) artist: String,
        @Path(value = "title", encoded = true) title: String
    ): Response<JsonObject>

}