package com.dherediat97.birdly.data.network

import com.dherediat97.birdly.domain.model.RecordedSound
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BirdRecordingService {
    @GET("recording/{id}")
    suspend fun getSingleRecord(@Path("id") id: Int): RecordedSound


    @GET("recordings?query=year:{c}:month={month}")
    suspend fun getRecentRecords(
        @Query("year") year: String,
        @Query("month") month: String
    ): List<RecordedSound>
}