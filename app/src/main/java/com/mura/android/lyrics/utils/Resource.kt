package com.mura.android.lyrics.utils

import com.mura.android.lyrics.data.model.Lyric
import retrofit2.Response

data class Resource<out T>(val status: Status, val code: Int?, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<Any> {

            return when (data) {
                is Response<*> -> {
                    val response = data as Response<*>
                    Resource(Status.SUCCESS, response.code(), response.body(), null)
                }

                is List<*> -> {
                    Resource(Status.SUCCESS, 0, data, null)
                }
                else -> {
                    Resource(Status.SUCCESS, 0, data, null)
                }
            }
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, 0, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, 0, data, null)
        }
    }
}