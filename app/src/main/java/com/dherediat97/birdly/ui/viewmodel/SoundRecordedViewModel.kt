package com.dherediat97.birdly.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.birdly.domain.model.RecordedSound
import com.dherediat97.birdly.domain.model.RequestRecordedSound
import com.dherediat97.birdly.domain.model.ResponseRecordedSounds
import com.dherediat97.birdly.domain.repository.BirdsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoundRecordedViewModel : ViewModel() {

    private val birdsRepo = BirdsRepository()

    private val _state = MutableStateFlow(RecordUIState())
    val uiState: StateFlow<RecordUIState>
        get() = _state

    private val _stateRecords = MutableStateFlow(ResponseRecordedSounds())
    val uiStateRecords: StateFlow<ResponseRecordedSounds>
        get() = _stateRecords

    fun fetchBirdSound(recordId: Int) {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                val record = birdsRepo.getSingleRecord(recordId)
                _state.update {
                    it.copy(record = record)
                }
            }
        }.onFailure {
            error(it)
        }
    }

    fun fetchNearRecords(lat: String, lon: String) = viewModelScope.launch {
        runCatching {
            viewModelScope.launch(Dispatchers.IO) {
                val recordList = birdsRepo.getMultipleRecords(
                    RequestRecordedSound(
                        lat = lat,
                        lon = lon,
                    ).toString()
                )
                _stateRecords.update {
                    it.copy(
                        numRecordings = recordList.numRecordings,
                        numSpecies = recordList.numSpecies,
                        numPages = recordList.numPages,
                        recordings = recordList.recordings,
                        page = recordList.page
                    )
                }
            }
        }.onFailure {
            error(it)
        }
    }

    fun setDefaultUiState() {
        _state.update { it.copy(record = RecordedSound()) }
    }


    data class RecordUIState(
        val record: RecordedSound = RecordedSound()
    )
}