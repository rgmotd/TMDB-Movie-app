package com.example.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.data.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: Result)

    @Query("SELECT * FROM movies WHERE is_saved = :is_saved")
    fun getSavedMovies(is_saved: Boolean = true): LiveData<List<Result>>

    @Delete
    suspend fun delete(result: Result)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id: Int): Flow<Result?>
}