package com.mura.android.lyrics.lyric.ui.lyric

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.mura.android.lyrics.lyric.domain.usecase.FindLyricUseCase
import com.mura.android.lyrics.utils.remote.ResultManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class LyricFindWorkManager @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters,
    private val findLyricUseCase: FindLyricUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val artists = inputData.getString(KEY_ARTIST)
            val title = inputData.getString(KEY_TITLE)

            when (val result = findLyricUseCase.findLyricByParams(artists!!, title!!)) {
                is ResultManager.Success -> {
                    val data = Data.Builder()
                        .putString(KEY_RESULT, result.items!!.body().toString())
                        .build()
                    Result.success(data)
                }
                is ResultManager.Error -> {
                    val data = Data.Builder()
                        .putString(KEY_ERROR, result.errorMessage)
                        .build()
                    Result.failure(data)
                }
                else -> {
                    val data = Data.Builder()
                        .putString(KEY_ERROR, "Error Occurred!")
                        .build()
                    Result.failure(data)
                }
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val KEY_ARTIST = "KEY_ARTIST"
        const val KEY_TITLE = "KEY_TITLE"
        const val KEY_RESULT = "KEY_RESULT"
        const val KEY_ERROR = "KEY_ERROR"
        const val FIND_LYRIC_WORK_TAG = "FIND_LYRIC_WORK_TAG"

    }

}