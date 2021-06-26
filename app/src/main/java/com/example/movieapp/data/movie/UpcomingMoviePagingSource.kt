package com.example.movieapp.data.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.api.MovieAPI
import com.example.movieapp.data.Result
import retrofit2.HttpException
import java.io.IOException

class UpcomingMoviePagingSource(
    private val movieApi: MovieAPI
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: 1

        return try {
            val response = movieApi.getUpcomingMovies(page = position)
            val movies = response.body()!!.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }
}