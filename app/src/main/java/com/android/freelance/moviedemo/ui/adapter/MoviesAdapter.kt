package com.android.freelance.moviedemo.ui.activities.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
/*import android.support.v7.widget.RecyclerView*/
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.moviedemo.R
import com.android.freelance.moviedemo.data.db.entity.Data
import com.squareup.picasso.Picasso


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val LOG_TAG = MoviesAdapter::class.java.name
    private val moviesDataList: MutableList<Data> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() is called...")

        val layoutInflater = LayoutInflater.from(parent.context) //Gave me a hard time
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movies_list, parent, false))
    }

    override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() is called...")

        return moviesDataList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, i: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() is called...")

        holder.bindModel(moviesDataList[i])
    }

    fun setMovies(dataList: List<Data>) {
        Log.i(LOG_TAG, "TEST: setMovies() is called...")

        moviesDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val LOG_TAG = MovieViewHolder::class.java.name

        private val poster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        private val title: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val genre: TextView = itemView.findViewById(R.id.tvMovieGenre)
        private val year: TextView = itemView.findViewById(R.id.tvMovieYear)

        fun bindModel(movie: Data) {
            Log.i(LOG_TAG, "TEST: bindModel() is called...")

            Picasso.get().load(movie.mPoster).into(poster)
            title.text = movie.mTitle
            genre.text = movie.mGenre
            year.text = movie.mYear
        }
    }
}