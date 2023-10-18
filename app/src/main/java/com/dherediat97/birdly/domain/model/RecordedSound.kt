package com.dherediat97.birdly.domain.model


data class RecordedSound(
    val id: Int = 0,
    val en: String = "",
    val loc: String = "",
    val gen: String = "",
    val rec: String = "",
    val group: String = "",
    val url: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val img: String = ""
)