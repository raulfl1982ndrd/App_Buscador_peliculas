package com.example.app_buscador_peliculas.data

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("Search")val results: List<Movie>,
    @SerializedName("Response") val Response: String,
    @SerializedName("totalResults")val totalResults: String
){
    class Movie (
        @SerializedName("Title") val title:String,
        @SerializedName("Year") val year:String,
        @SerializedName("imdbID") val imdbID:String,
        @SerializedName("Type") val type:String,
        @SerializedName("Plot") val sinopsis:String,
        @SerializedName("Runtime") val duracion:String,
        @SerializedName("Director") val director:String,
        @SerializedName("Genre") val genero:String,
        @SerializedName("Country") val pais:String,
        @SerializedName("Poster") val poster:String)
    }