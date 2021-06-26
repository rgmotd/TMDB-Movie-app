package com.example.movieapp.api

import com.example.movieapp.data.cast.CastResponse
import com.example.movieapp.data.Constants.API_KEY
import com.example.movieapp.data.Result
import com.example.movieapp.data.movie.MovieResponse
import com.example.movieapp.data.moviedetails.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("page")
        page: Int
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("region")
        region: String = "US"
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("page")
        page: Int
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchForMovies(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("page")
        page: Int = 1,
        @Query("query")
        searchQuery: String
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<CastResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<MovieDetailsResponse>
}