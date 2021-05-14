package com.mura.android.lyrics.ui.viewModel

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.gson.Gson
import com.mura.android.lyrics.data.model.Lyric
import com.mura.android.lyrics.data.repository.DataBaseRepository
import com.mura.android.lyrics.data.repository.LyricRepository
import com.mura.android.lyrics.data.repository.Repository
import com.mura.android.lyrics.utils.NetworkHelper
import com.mura.android.lyrics.utils.Resource
import com.mura.android.lyrics.utils.SingleLiveEvent
import com.mura.android.lyrics.utils.workermanager.TrackLocationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val gson: Gson,
    private val appContext: Application,
    private val locationRequest: LocationRequest,
    private val repository: Repository
) : ViewModel() {

    var lyric = MutableLiveData<Resource<Lyric>>()

    var error = MutableLiveData<Resource<Any>>()

    val eventMsg = SingleLiveEvent<String>()

    val enableLocation: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    val location: MutableLiveData<Resource<List<Location>>> = MutableLiveData()

    val responseDatabase =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(Resource.success(repository.dataBaseRepository.getAllLyrics()))
        }

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
        enableLocation.value = Resource.loading()
        LocationServices.getSettingsClient(appContext)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .setAlwaysShow(true)
                    .build()
            )
            .addOnSuccessListener { enableLocation.value = Resource.success(true) }
            .addOnFailureListener {
                //Timber.e(it, "Gps not enabled")
                enableLocation.value = Resource.error(false)
            }
    }

    fun trackLocation() {

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val locationWorker =
            PeriodicWorkRequestBuilder<TrackLocationWorker>(15, TimeUnit.MINUTES).addTag(
                LOCATION_WORK_TAG
            ).setConstraints(constraints).build()


        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
            LOCATION_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            locationWorker
        )
    }

    fun stopTrackLocation() {
        WorkManager.getInstance(appContext).cancelAllWorkByTag(LOCATION_WORK_TAG)
    }

    fun getSavedLocation() {

    }

    companion object {
        private val TAG = this::class.java.canonicalName!!
        const val internetErr = "La red no funciona.\n" +
                "Verifica tu cconexión"
        const val otherErr = "Error Occurred!"
        const val LOCATION_WORK_TAG = "LOCATION_WORK_TAG"

    }
}