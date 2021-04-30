package com.mura.android.lyrics.data.remote

import javax.inject.Inject

class ApiServiceDataSource @Inject constructor(
    private val apiServices: ApiServices
) : ApiServiceDataSourceImp {

    override suspend fun getLyricByArtistAndTitle(artist: String, title: String) =
        apiServices.getLyricByArtistAndTitle(artist,title)
}