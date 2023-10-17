package com.dherediat97.birdly.data.network

import com.dherediat97.birdly.domain.model.BirdRecordedSound
import retrofit2.http.GET
import retrofit2.http.Path

interface BirdRecordingService {
    @GET("recording/{id}")
    suspend fun getSingleRecord(@Path("id") id: Int): BirdRecordedSound
}