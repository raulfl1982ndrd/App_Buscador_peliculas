package com.example.superheroesapp.utils

import com.example.app_buscador_peliculas.data.MovieApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object{
        private fun getRetrofit(): Retrofit {
            /*val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/
            return Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/?apikey=14755002")
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getMovieApiService(): MovieApiService {
            return getRetrofit().create(MovieApiService::class.java)
        }

    }
}