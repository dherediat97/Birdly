package com.dherediat97.birdly.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.birdly.domain.model.RecordedSound
import com.dherediat97.birdly.domain.repository.BirdsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoundRecordedViewModel : ViewModel() {

    private val birdsRepo = BirdsRepository()

    private val _state = MutableStateFlow(RecordUIState())
    val uiState: StateFlow<RecordUIState>
        get() = _state

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


    data class RecordUIState(
        val record: RecordedSound = RecordedSound()
    )
}