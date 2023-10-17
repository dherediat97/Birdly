package com.dherediat97.birdly.di

import com.dherediat97.birdly.domain.repository.BirdsRepository
import org.koin.dsl.module


val repositoriesModule = module {
    single { BirdsRepository() }
}