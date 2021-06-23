package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movieapp.api.MovieAPI
import com.example.movieapp.data.Constants.API_KEY
import com.example.movieapp.data.movie.MoviePagingSource
import com.example.movieapp.db.MovieDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val api: MovieAPI,
    private val db: MovieDatabase
) {
    fun getPopularMovies() = Pager(
        config = PagingConfig(pageSize = 18, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(api) }).liveData

    suspend fun getTopRatedMovies() = api.getTopRatedMovies(API_KEY)

    suspend fun getMovieCast(id: Int) = api.getMovieCast(movie_id = id)
}