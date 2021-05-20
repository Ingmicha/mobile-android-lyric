package com.mura.android.lyrics.lyric.domain.usecase

import com.mura.android.lyrics.data.repository.Repository
import com.mura.android.lyrics.lyric.data.database.LyricDao
import com.mura.android.lyrics.lyric.domain.model.Lyric
import javax.inject.Inject

class InsetLyricUseCase @Inject constructor(
    private val dao: LyricDao
) {

    suspend fun insertLyric(lyric: Lyric) =
        dao.insert(lyric)

}