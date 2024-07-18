package com.example.app_buscador_peliculas.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("https://www.omdbapi.com/?apikey=14755002")
    suspend fun findMoviesByTitle(@Query("s") query: String) : MovieResponse

    @GET("https://www.omdbapi.com/?apikey=14755002")
    suspend fun getSMovieByimdbID(@Query("i") query: String) : MovieResponse.Movie


}