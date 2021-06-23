package com.example.movieapp.ui.viewmodels

import androidx.lifecycle.*
import com.example.movieapp.data.cast.CastResponse
import com.example.movieapp.data.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class CastViewModel @AssistedInject constructor(
    private val repository: MovieRepository,
    @Assisted private val movieId: Int
) : ViewModel() {
    class Factory(
        private val factory: CastViewModelAssistedFactory,
        private val movieId: Int,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return factory.create(movieId) as T
        }
    }

    val movieCast: MutableLiveData<CastResponse> = MutableLiveData()

    init {
        getMovieCast(movieId)
    }

    fun getMovieCast(movieId: Int) = viewModelScope.launch {
        movieCast.postValue(repository.getMovieCast(movieId).body())
    }
}
