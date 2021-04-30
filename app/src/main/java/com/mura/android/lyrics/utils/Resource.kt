package com.mura.android.lyrics.utils

import retrofit2.Response

data class Resource<out T>(val status: Status, val code: Int?, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<Any> {
            val response = data as Response<*>
            response.code()
            return Resource(Status.SUCCESS, response.code(), data.body(), null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, 0, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, 0, data, null)
        }
    }
}