package com.mura.android.lyrics.lyric.ui.lyric

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.lyric.domain.usecase.InsetLyricUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InsertLyricWorkManager @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val insetLyricUseCase: InsetLyricUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val lyric = inputData.getString(KEY_LYRIC)
            insetLyricUseCase.insertLyric(Gson().fromJson(lyric, Lyric::class.java))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_LYRIC = "JSON"
        const val INSERT_LYRIC_WORK_TAG = "INSERT_LYRIC_WORK_TAG"

    }

}