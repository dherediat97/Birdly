package com.dherediat97.birdly.di

import com.dherediat97.birdly.ui.viewmodel.SoundRecordedViewModel
import com.dherediat97.birdly.ui.viewmodel.RecentBirdsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        SoundRecordedViewModel()
    }
    viewModel {
        RecentBirdsViewModel()
    }
}