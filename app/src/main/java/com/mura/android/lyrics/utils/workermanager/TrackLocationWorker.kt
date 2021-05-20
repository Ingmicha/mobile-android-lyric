package com.mura.android.lyrics.utils.workermanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mura.android.lyrics.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class TrackLocationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    @Inject
    lateinit var repository: Repository

    override fun doWork(): Result {
        return try {
            val request = inputData.getInt("KEY", 0)

            repository.location.getLocation()
            val data = Data.Builder()
                .putString("KEY", "DATA")
                .build()
            Result.success(data)
        } catch (e: Exception) {
            Log.d("WORK-MANAGER", e.message!!)
            Result.failure()
        }
    }
}