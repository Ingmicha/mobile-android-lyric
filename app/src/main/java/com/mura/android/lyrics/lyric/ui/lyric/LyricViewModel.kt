package com.mura.android.lyrics.lyric.ui.lyric

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.gson.Gson
import com.mura.android.lyrics.lyric.domain.model.Lyric
import com.mura.android.lyrics.lyric.domain.usecase.FindLyricUseCase
import com.mura.android.lyrics.lyric.domain.usecase.GetLyricsUseCase
import com.mura.android.lyrics.lyric.domain.usecase.InsetLyricUseCase
import com.mura.android.lyrics.lyric.ui.lyric.InsertLyricWorkManager.Companion.INSERT_LYRIC_WORK_TAG
import com.mura.android.lyrics.lyric.ui.lyric.InsertLyricWorkManager.Companion.KEY_LYRIC
import com.mura.android.lyrics.lyric.ui.lyric.LyricFindWorkManager.Companion.FIND_LYRIC_WORK_TAG
import com.mura.android.lyrics.ui.viewModel.MainViewModel
import com.mura.android.lyrics.utils.remote.ResultManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    private val findLyricUseCase: FindLyricUseCase,
    private val getLyricsUseCase: GetLyricsUseCase,
    private val insetLyricUseCase: InsetLyricUseCase,
    private val gson: Gson,
    private val appContext: Application
) : MainViewModel() {

    private val _lyric = MutableLiveData<Lyric>()
    val lyric: LiveData<Lyric>
        get() = _lyric

    private val _lyricList = MutableLiveData<List<Lyric>>()
    val lyricList: LiveData<List<Lyric>>
        get() = _lyricList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var job: Job? = null

    fun getLyricsFromDB() {
        _isLoading.value = true
        job = viewModelScope.launch {
            val result = getLyricsUseCase.getLyrics()
            _isLoading.postValue(false)
            when (result) {
                is ResultManager.Success -> _lyricList.postValue(result.items!!)
                is ResultManager.Error -> _error.postValue(result.errorMessage)
            }
        }
    }

    fun getLyricByArtisTitle(artist: String, title: String) {
        _isLoading.value = true
        job = viewModelScope.launch {
            val result = findLyricUseCase.findLyricByParams(artist, title)
            _isLoading.postValue(false)
            when (result) {
                is ResultManager.Success -> {
                    _lyric.postValue(
                        gson.fromJson(
                            result.items!!.body().toString(),
                            Lyric::class.java
                        ).apply {
                            this.artist = artist
                            this.title = title
                        }
                    )
                    useWorkerManager()
                }
                is ResultManager.Error ->
                    _error.postValue(result.errorMessage)
            }
        }
    }

    private fun useWorkerManager() {

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val insertLyricWorker = OneTimeWorkRequestBuilder<InsertLyricWorkManager>()
            .addTag(INSERT_LYRIC_WORK_TAG)
            .setConstraints(constraints)
            .setInputData(workDataOf(KEY_LYRIC to gson.toJson(lyric.value)))
            .build()

        WorkManager.getInstance(appContext)
            .enqueue(insertLyricWorker)

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}