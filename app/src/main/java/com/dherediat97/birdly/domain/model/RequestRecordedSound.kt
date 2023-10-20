package com.dherediat97.birdly.domain.model

data class RequestRecordedSound(
    val lat: String = "",
    val lon: String = "",
) {
    override fun toString(): String {
        return "lat:$lat%20lon:$lon"
    }
}