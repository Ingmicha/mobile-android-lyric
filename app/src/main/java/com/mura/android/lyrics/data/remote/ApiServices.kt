package com.mura.android.lyrics.data.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("{artist}/{title}")
    suspend fun getLyricByArtistAndTitle(
        @Path(value = "artist", encoded = true) artist: String,
        @Path(value = "title", encoded = true) title: String
    ): Response<JsonObject>

}