package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movieapp.api.MovieAPI
import com.example.movieapp.data.Constants.API_KEY
import com.example.movieapp.data.Result
import com.example.movieapp.data.movie.MoviePagingSource
import com.example.movieapp.data.movie.UpcomingMoviePagingSource
import com.example.movieapp.db.MovieDatabase
import kotlinx.coroutines.flow.*
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

    fun getUpcomingMovies() = Pager(
        config = PagingConfig(pageSize = 18, enablePlaceholders = false),
        pagingSourceFactory = { UpcomingMoviePagingSource(api) }).liveData

    suspend fun getTopRatedMovies() = api.getTopRatedMovies(API_KEY)

    suspend fun getMovieCast(id: Int) = api.getMovieCast(movie_id = id)

    suspend fun insert(result: Result) = db.getMovieDao().insert(result)

    fun getSavedMovies() = db.getMovieDao().getSavedMovies()

    suspend fun delete(result: Result) = db.getMovieDao().delete(result)

    suspend fun getMovie(id: Int) = db.getMovieDao().getMovie(id).transform {
        if (it == null) {
            val movieApi = api.getMovie(movie_id = id).body()!!
            insert(movieApi.toResult())
        } else emit(it)
    }
}