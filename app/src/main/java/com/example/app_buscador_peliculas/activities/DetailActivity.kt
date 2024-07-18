package com.example.app_buscador_peliculas.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_buscador_peliculas.R
import com.example.app_buscador_peliculas.data.MovieResponse
import com.example.app_buscador_peliculas.databinding.ActivityDetailBinding
import com.example.superheroesapp.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var movie: MovieResponse.Movie

    /**
     * width of the main web lines
     */
    private var mWebLineWidth = 2.5f

    companion object {
        const val EXTRA_MOVIE_imdbID:String  = "EXTRA_MOVIE_imdbID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
        val imdbID: String = intent.getStringExtra(EXTRA_MOVIE_imdbID)!!
        getMovieById(imdbID)

    }

    private fun loadData() {
        binding.tittleDetailTextView.text = movie.title
        Picasso.get().load(movie.poster).into(binding.photoImageView)

        // Biography
        binding.content.anoTextView.text = movie.year
        binding.content.sinopsisTextView.text = movie.sinopsis
        binding.content.duracionTextView.text = movie.duracion
        binding.content.directorTextView.text = movie.director
        binding.content.generoTextView.text = movie.genero
        binding.content.paisTextView.text = movie.pais
    }
    fun getMovieById(imdbID:String){

        binding.content.progressBar.visibility = View.GONE
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetrofitProvider.getMovieApiService()
                val result = apiService.getSMovieByimdbID(imdbID)
                movie = result
                runOnUiThread {

                    Toast.makeText(this@DetailActivity,result.title, Toast.LENGTH_LONG).show()
                    loadData()
                }
                Log.i("HTTP", "${result.imdbID}-->${result.title}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}