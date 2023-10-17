package com.dherediat97.birdly.domain.repository

import com.dherediat97.birdly.data.network.RetrofitInstance

class BirdsRepository {

    private val recordsService = RetrofitInstance.birdsService

    suspend fun getSingleRecord(id: Int) = recordsService.getSingleRecord(id)
}