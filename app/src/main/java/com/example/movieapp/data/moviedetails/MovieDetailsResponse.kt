package com.example.movieapp.data.moviedetails

import com.example.movieapp.data.Result

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: Any,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toResult(): Result {
        return Result(
            id = id,
            adult = adult,
            backdrop_path = backdrop_path,
            original_language = original_language,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path as String,
            release_date = release_date,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count,
            genre_ids = genres.map { it.id },
            is_saved = false
        )
    }
}

