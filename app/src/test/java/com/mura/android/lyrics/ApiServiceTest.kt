package com.mura.android.lyrics

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mura.android.lyrics.data.remote.ApiServices
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceTest {

    lateinit var apiServices: ApiServices

    lateinit var okHttpClient: OkHttpClient

    var baseURL: String = "https://api.lyrics.ovh/v1/"

    @BeforeEach
    internal fun buildOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
        this.okHttpClient = okHttpClient
    }

    @BeforeEach
    internal fun buildRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)

    }

    @Test
    internal fun callServiceLyricByArtistAndTitle() {
        runBlocking {

            val response: JsonObject = JsonParser.parseString(
                apiServices.getLyricByArtistAndTitle(
                    "Foo fighters",
                    "Best of you"
                ).body().toString()
            ).asJsonObject
            println(response)
        }
    }

}