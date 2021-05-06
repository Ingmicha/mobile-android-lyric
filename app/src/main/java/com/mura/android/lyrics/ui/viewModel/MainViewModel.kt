package com.mura.android.lyrics.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mura.android.lyrics.data.model.Lyric
import com.mura.android.lyrics.data.repository.DataBaseRepository
import com.mura.android.lyrics.data.repository.LyricRepository
import com.mura.android.lyrics.utils.NetworkHelper
import com.mura.android.lyrics.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val lyricRepository: LyricRepository,
    private val networkHelper: NetworkHelper,
    private val dataBaseRepository: DataBaseRepository,
    private val gson: Gson
) : ViewModel() {

    var response = MutableLiveData<Resource<Any>>()

    var list = MutableLiveData<List<Lyric>>()

    var lyric = MutableLiveData<String>()

    var error = MutableLiveData<Resource<Any>>()

    suspend fun onGetHistorySearch() {
        viewModelScope.launch {
            list.postValue(dataBaseRepository.getAllLyrics())
        }
    }

    suspend fun onSearchByArtistAndTitle(artist: String, title: String) {

        response.postValue(Resource.loading(null))

        if (networkHelper.isNetworkConnected()) {

            viewModelScope.launch {
                try {
                    response.value = Resource.success(
                        lyricRepository.findLyricByArtistAndTitle(
                            artist,
                            title
                        )
                    )

                    if (response.value!!.code == 200) {

                        val lyricResponse: String = gson.fromJson(
                            response.value!!.data.toString(),
                            Lyric::class.java
                        ).lyric

                        dataBaseRepository.insetLyric(
                            Lyric(
                                artist = artist,
                                title = title,
                                lyric = lyricResponse
                            )
                        )

                        lyric.postValue(lyricResponse)

                    } else {
                        error.postValue(
                            Resource.error(
                                data = null,
                                message = "No se encontro algun resultado"
                            )
                        )
                    }

                } catch (e: Exception) {
                    error.postValue(
                        Resource.error(
                            data = null,
                            message = e.message ?: otherErr
                        )
                    )
                }
            }

        } else {

            error.postValue(
                Resource.error(
                    data = null,
                    message = internetErr
                )
            )

        }

    }

    companion object {
        private val TAG = this::class.java.canonicalName!!
        const val internetErr = "La red no funciona.\n" +
                "Verifica tu cconexión"
        const val otherErr = "Error Occurred!"
    }
}