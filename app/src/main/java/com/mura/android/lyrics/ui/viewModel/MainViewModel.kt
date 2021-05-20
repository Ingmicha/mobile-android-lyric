package com.mura.android.lyrics.ui.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import androidx.work.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.gson.Gson
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.data.repository.Repository
import com.mura.android.lyrics.utils.NetworkHelper
import com.mura.android.lyrics.utils.Resource
import com.mura.android.lyrics.utils.SingleLiveEvent
import com.mura.android.lyrics.utils.workermanager.TrackLocationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
open class MainViewModel : ViewModel() {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var locationRequest: LocationRequest


    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    suspend fun onSearchByArtistAndTitle(artist: String, title: String) =

        viewModelScope.launch {
            Resource.loading<Boolean>()
            if (networkHelper.isNetworkConnected()) {

                val response = repository.lyricRepository.findLyricByArtistAndTitle(
                    artist,
                    title
                )

                if (response.code() == 200) {

                    val lyricResponse: String = gson.fromJson(
                        response.body()!!.asJsonObject,
                        Lyric::class.java
                    ).lyric

                    repository.dataBaseRepository.insetLyric(
                        Lyric(
                            artist = artist,
                            title = title,
                            lyric = lyricResponse
                        )
                    )

                } else {
                    error.value =
                        Resource.error(otherErr)
                }

            } else {
                eventMsg.value = internetErr
            }
        }

    fun locationSetup() {
        LocationServices.getSettingsClient(application)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .setAlwaysShow(true)
                    .build()
            )
            .addOnSuccessListener {
                //enableLocation.value = Resource.success(true)
            }
            .addOnFailureListener {
                //Timber.e(it, "Gps not enabled")
                //enableLocation.value = Resource.error(false)
            }
    }

    fun trackLocation() {

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val locationWorker =
            PeriodicWorkRequestBuilder<TrackLocationWorker>(15, TimeUnit.MINUTES).addTag(
                LOCATION_WORK_TAG
            ).setConstraints(constraints).build()


        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            LOCATION_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            locationWorker
        )
    }

    fun stopTrackLocation() {
        WorkManager.getInstance(application).cancelAllWorkByTag(LOCATION_WORK_TAG)
    }

    companion object {
        private val TAG = this::class.java.canonicalName!!
        const val internetErr = "La red no funciona.\n" +
                "Verifica tu cconexión"
        const val otherErr = "Error Occurred!"
        const val LOCATION_WORK_TAG = "LOCATION_WORK_TAG"

    }
}