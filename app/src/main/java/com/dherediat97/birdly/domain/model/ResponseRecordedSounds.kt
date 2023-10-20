package com.dherediat97.birdly.domain.model

data class ResponseRecordedSounds(
    val numRecordings: String = "",
    val numSpecies: String = "",
    val page: Int = 0,
    val numPages: Int = 0,
    val recordings: List<RecordedSound> = emptyList()
)