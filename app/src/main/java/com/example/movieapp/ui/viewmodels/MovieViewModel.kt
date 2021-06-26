package com.example.movieapp.ui.viewmodels

import androidx.lifecycle.*
import com.example.movieapp.data.Result
import com.example.movieapp.data.movie.MovieResponse
import com.example.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel(), LifecycleObserver {
    val topRatedMovies: MutableLiveData<MovieResponse> = MutableLiveData()

    init {
        getTopRatedMovies()
    }

    fun getPopularMovies() = repository.getPopularMovies()

    fun getTopRatedMovies() = viewModelScope.launch {
        topRatedMovies.postValue(repository.getTopRatedMovies().body())
    }

    fun getUpcomingMovies() = repository.getUpcomingMovies()

    fun deleteMovie(result: Result) = viewModelScope.launch {
        repository.delete(result)
    }

    fun getSavedMovies() = repository.getSavedMovies()
}