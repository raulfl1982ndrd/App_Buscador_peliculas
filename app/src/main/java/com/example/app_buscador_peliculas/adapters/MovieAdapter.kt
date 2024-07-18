package com.example.app_buscador_peliculas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_buscador_peliculas.data.MovieResponse.Movie
import com.example.app_buscador_peliculas.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter (private var dataSet: List<Movie> = emptyList(),
                    private val onItemClickListener: (Int) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val superheroe = dataSet[position]
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(holder.adapterPosition)
        }
    }

    fun updateData(dataSet: List<Movie>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(movie: Movie) {
        binding.titleTextView.text = movie.title
        binding.yearTextView.text = movie.year
        Picasso.get().load(movie.poster).into(binding.movieImageView)    }
}