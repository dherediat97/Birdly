package com.dherediat97.birdly.data.network

import com.dherediat97.birdly.domain.model.RecordedSound
import com.dherediat97.birdly.domain.model.ResponseRecordedSounds
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface BirdRecordingService {
    @GET("recording/{id}")
    suspend fun getSingleRecord(@Path("id") id: Int): RecordedSound

    @GET("recordings")
    suspend fun getMultipleRecords(@Query("query", encoded = true) query: String):ResponseRecordedSounds
}