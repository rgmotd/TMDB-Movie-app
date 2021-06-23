package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.api.MovieAPI
import com.example.movieapp.data.Constants
import com.example.movieapp.db.MovieDao
import com.example.movieapp.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(MovieAPI::class.java)

    @Provides
    @Singleton
    fun provideChannelDao(db: MovieDatabase): MovieDao = db.getMovieDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "MovieDB"
        ).build()
    }
}