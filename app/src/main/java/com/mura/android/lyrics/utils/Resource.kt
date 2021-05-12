package com.mura.android.lyrics.utils

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {
    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {
        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null, null)
        fun <T> success(data: T? = null): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> error(data: T?, error: Throwable): Resource<T> = Resource(Status.ERROR, data, error)
        fun <T> error(data: T?): Resource<T> = Resource(Status.ERROR, data, null)
    }
}