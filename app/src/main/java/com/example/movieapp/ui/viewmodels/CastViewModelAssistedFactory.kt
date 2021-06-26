package com.example.movieapp.ui.viewmodels

import dagger.assisted.AssistedFactory

@AssistedFactory
interface CastViewModelAssistedFactory {
    fun create(id: Int): MovieDetailsViewModel
}