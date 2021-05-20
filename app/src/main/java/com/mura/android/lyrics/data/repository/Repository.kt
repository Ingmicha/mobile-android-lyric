package com.mura.android.lyrics.data.repository

import com.mura.android.lyrics.location.data.repository.LocationRepository
import com.mura.android.lyrics.lyric.data.reposiroty.LyricRepository
import javax.inject.Inject

class Repository @Inject constructor(
    val lyricRepository: LyricRepository,
    val location: LocationRepository
)