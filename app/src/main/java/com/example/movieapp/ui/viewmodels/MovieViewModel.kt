package com.example.movieapp.ui.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        getUpcomingMovies()
    }

    fun getPopularMovies() = repository.getPopularMovies()

    fun getTopRatedMovies() = viewModelScope.launch {
        topRatedMovies.postValue(repository.getTopRatedMovies().body())
    }

    fun getUpcomingMovies() = repository.getUpcomingMovies()
}