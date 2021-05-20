package com.mura.android.lyrics.lyric.domain.usecase

import com.mura.android.lyrics.data.repository.Repository
import com.mura.android.lyrics.lyric.domain.model.Lyric
import javax.inject.Inject

class GetLyricsUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getLyrics() =
        repository.lyricRepository.getLyricsFromDB()

}