package com.example.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.data.Result

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: Result): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Result>>

    @Delete
    suspend fun delete(result: Result)
}