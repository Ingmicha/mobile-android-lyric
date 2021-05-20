package com.mura.android.lyrics.lyric.data.reposiroty

import com.google.gson.JsonObject
import com.mura.android.lyrics.lyric.data.api.LyricApiDataSource
import com.mura.android.lyrics.lyric.data.database.LyricDao
import com.mura.android.lyrics.lyric.domain.repository.LyricRepositoryImp
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.utils.extentions.safeApiCall
import com.mura.android.lyrics.utils.remote.ResultManager
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LyricRepository @Inject constructor(
    private val lyricApiDataSource: LyricApiDataSource,
    private val dao: LyricDao
) : LyricRepositoryImp {

    override suspend fun getLyric(
        artist: String,
        title: String
    ): ResultManager<Response<JsonObject>> {
        return safeApiCall(
            call = {
                ResultManager.Success(
                    lyricApiDataSource.getLyricByArtistAndTitle(
                        artist,
                        title
                    )
                )
            },
            errorMessage = "Exception occurred!"
        )
    }

    override suspend fun getLyricsFromDB(): ResultManager<List<Lyric>> =
        safeApiCall(
            call = { dao.getAllLyrics() },
            errorMessage = "Exception occurred!"
        )

    override suspend fun insertLyric(lyric: Lyric): ResultManager<Long> =
        safeApiCall(
            call = { dao.insert(lyric) },
            errorMessage = "Exception occurred!"
        )

}