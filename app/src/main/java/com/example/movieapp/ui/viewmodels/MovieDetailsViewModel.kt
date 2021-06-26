package com.example.movieapp.ui.viewmodels

import androidx.lifecycle.*
import com.example.movieapp.data.Result
import com.example.movieapp.data.cast.CastResponse
import com.example.movieapp.data.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    private val repository: MovieRepository,
    @Assisted private val movieId: Int
) : ViewModel() {
    private val _movie: MutableLiveData<Result> = MutableLiveData()
    val movie: LiveData<Result> get() = _movie

    private val _movieCast: MutableLiveData<CastResponse> = MutableLiveData()
    val movieCast: LiveData<CastResponse> get() = _movieCast

    init {
        getMovie()
        getMovieCast(movieId)
    }

    class Factory(
        private val factory: CastViewModelAssistedFactory,
        private val movieId: Int,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return factory.create(movieId) as T
        }
    }

    fun getMovieCast(movieId: Int) = viewModelScope.launch {
        _movieCast.postValue(repository.getMovieCast(movieId).body())
    }

    fun getMovie() = viewModelScope.launch(Dispatchers.IO) {
        repository.getMovie(movieId).collect {
            _movie.postValue(it)
        }
    }

    fun insert(result: Result) = viewModelScope.launch {
        repository.insert(result)
    }
}
