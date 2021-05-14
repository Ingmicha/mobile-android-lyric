package com.mura.android.lyrics.data.repository

import javax.inject.Inject

class Repository @Inject constructor(
    val dataBaseRepository: DataBaseRepository,
    val lyricRepository: LyricRepository,
    val location: LocationRepository
)