package com.dherediat97.birdly.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dherediat97.birdly.domain.model.RecordedSound
import com.dherediat97.birdly.domain.repository.BirdsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecentBirdsViewModel : ViewModel() {

    private val birdsRepo = BirdsRepository()

    private val _state = MutableStateFlow(RecordsUIState())
    val uiState: StateFlow<RecordsUIState>
        get() = _state

//    fun fetchRecentRecords() {
//        runCatching {
//            viewModelScope.launch(Dispatchers.IO) {
//                val records = birdsRepo.getRecentRecords()
//                _state.update {
//                    it.copy(records = records)
//                }
//            }
//        }.onFailure {
//            error(it)
//        }
//    }

    data class RecordsUIState(
        val records: List<RecordedSound> = mutableListOf(
            RecordedSound(
                en = "Zorzal alirrojo",
                img = "https://upload.wikimedia.org/wikipedia/commons/c/c1/Redwing_Turdus_iliacus.jpg"
            ),
            RecordedSound(
                en = "Solitario ocre brasileño",
                img = "https://live.staticflickr.com/65535/48679857013_99dd8fdb76_b.jpg"
            ),
            RecordedSound(
                en = "Barbudo pintado",
                img = "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/204745771"
            ),
            RecordedSound(
                en = "Conirrostro orejiblanco",
                img = "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/147267561/1200"
            ),
            RecordedSound(
                en = "Mosquero cordillerano",
                img = "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/301864901/1200"
            ),
            RecordedSound(
                en = "Carbonero garrapinos",
                img = "https://seo.org/wp-content/uploads/2013/11/F486_Foto_01.jpg"
            ),
        )
    )
}