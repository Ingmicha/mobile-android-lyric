package com.mura.android.lyrics.lyric.data.api

import javax.inject.Inject

class LyricApiDataSource @Inject constructor(
    private val lyricApi: LyricApi
) {
    suspend fun getLyricByArtistAndTitle(artist: String, title: String) =
        lyricApi.getLyricByArtistAndTitle(artist, title)
}