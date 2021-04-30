package com.mura.android.lyrics.data.remote

import com.google.gson.JsonObject
import retrofit2.Response

interface ApiServiceDataSourceImp {

    suspend fun getLyricByArtistAndTitle(artist: String, title: String): Response<JsonObject>

}