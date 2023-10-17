package com.dherediat97.birdly.di

import com.dherediat97.birdly.ui.viewmodel.NearBirdsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        NearBirdsViewModel()
    }
}