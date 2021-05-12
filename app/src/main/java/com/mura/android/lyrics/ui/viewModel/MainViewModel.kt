package com.mura.android.lyrics.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mura.android.lyrics.data.model.Lyric
import com.mura.android.lyrics.data.repository.DataBaseRepository
import com.mura.android.lyrics.data.repository.LyricRepository
import com.mura.android.lyrics.utils.NetworkHelper
import com.mura.android.lyrics.utils.Resource
import com.mura.android.lyrics.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val lyricRepository: LyricRepository,
    private val networkHelper: NetworkHelper,
    private val dataBaseRepository: DataBaseRepository,
    private val gson: Gson
) : ViewModel() {

    var lyric = MutableLiveData<Resource<Lyric>>()

    var error = MutableLiveData<Resource<Any>>()

    val eventMsg = SingleLiveEvent<String>()

    val responseDatabase =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(Resource.success(dataBaseRepository.getAllLyrics()))
        }

    suspend fun onSearchByArtistAndTitle(artist: String, title: String) =

        viewModelScope.launch {
            Resource.loading<Boolean>()
            if (networkHelper.isNetworkConnected()) {

                val response = lyricRepository.findLyricByArtistAndTitle(
                    artist,
                    title
                )

                if (response.code() == 200) {

                    val lyricResponse: String = gson.fromJson(
                        response.body()!!.asJsonObject,
                        Lyric::class.java
                    ).lyric

                    dataBaseRepository.insetLyric(
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

    companion object {
        private val TAG = this::class.java.canonicalName!!
        const val internetErr = "La red no funciona.\n" +
                "Verifica tu cconexión"
        const val otherErr = "Error Occurred!"
    }
}