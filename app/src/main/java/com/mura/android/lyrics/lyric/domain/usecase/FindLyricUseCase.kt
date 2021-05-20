package com.mura.android.lyrics.lyric.domain.usecase

import com.google.gson.JsonObject
import com.mura.android.lyrics.data.repository.Repository
import com.mura.android.lyrics.utils.remote.ResultManager
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindLyricUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun findLyricByParams(
        artists: String,
        song: String
    ) =
        when (val result = (repository.lyricRepository.getLyric(artists, song))) {
            is ResultManager.Success -> {
                if (result.items!!.isSuccessful) {
                    result
                } else {
                    ResultManager.Error(result.items.code(), result.items.errorBody().toString())
                }
            }
            else -> {
                result
            }
        }
}