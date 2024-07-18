package com.example.app_buscador_peliculas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app_buscador_peliculas.R
import com.example.app_buscador_peliculas.adapters.MovieAdapter
import com.example.app_buscador_peliculas.data.MovieResponse
import com.example.app_buscador_peliculas.databinding.ActivityMainBinding
import com.example.superheroesapp.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var movieList: List<MovieResponse.Movie> = emptyList()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = MovieAdapter ( movieList){position->
            navigateToDetail(movieList[position])
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        /*binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchSuperheroes(searchText)
        }*/
        //searchByName("a")
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        initSearchView(menu?.findItem(R.id.menu_search))

        return true
    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            var searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query!=  null) {
                        //searchSuperheroes(query!!)
                        searchByName(query!!)
                    }
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }
    }


    private fun navigateToDetail(movie: MovieResponse.Movie) {
        Toast.makeText(this,movie.title,Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_imdbID, movie.imdbID)
        startActivity(intent)
    }

    private fun searchByName(query: String){
        binding.progress.visibility = View.VISIBLE
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetrofitProvider.getMovieApiService()
                val result = apiService.findMoviesByTitle(query)
                runOnUiThread {
                    binding.progress.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyPlaceholder.visibility = View.GONE
                    if(result.Response == "True")
                        movieList = result.results
                    else movieList = emptyList()

                    adapter.updateData(movieList)
                }
                Log.i("HTTP1", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}