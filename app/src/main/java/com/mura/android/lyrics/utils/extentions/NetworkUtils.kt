package com.mura.android.lyrics.utils.extentions

import java.io.IOException
import com.mura.android.lyrics.utils.remote.ResultManager

suspend fun <T : Any> safeApiCall(
    call: suspend () -> ResultManager<T>,
    errorMessage: String
): ResultManager<T> {
    return try {
        call()
    } catch (e: Exception) {
        ResultManager.Exception(IOException(errorMessage, e))
    }
}