package com.android.freelance.filmdemo.ui.activities.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
/*import android.support.v7.widget.RecyclerView*/
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.filmdemo.R
import com.android.freelance.filmdemo.data.db.entity.Films
import com.squareup.picasso.Picasso


class FilmsAdapters(private val film: List<Films>) : RecyclerView.Adapter<FilmsAdapters.MovieViewHolder>() {

    private val LOG_TAG = FilmsAdapters::class.java.name
    /*private val filmsDataList: MutableList<Films> = mutableListOf()*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() is called...")

        val layoutInflater = LayoutInflater.from(parent.context) //Gave me a hard time
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_films_list, parent, false))
    }

    /*override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() is called...")

        /*return filmsDataList.size*/
        return film.size
    }

    override fun getItemCount() =  filmsDataList.size*/

    override fun getItemCount() = film.size

    override fun onBindViewHolder(holder: MovieViewHolder, i: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() is called...")

        /*holder.bindModel(filmsDataList[i])*/
        holder.bindModel(film[i])
    }

/* fun setMovies(dataList: List<Data>) {
     Log.i(LOG_TAG, "TEST: setMovies() is called...")

     moviesDataList.addAll(dataList)
     notifyDataSetChanged()
 }*/

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val LOG_TAG = MovieViewHolder::class.java.name

        private val poster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        private val title: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val genre: TextView = itemView.findViewById(R.id.tvMovieGenre)
        private val year: TextView = itemView.findViewById(R.id.tvMovieYear)

        @SuppressLint("SetTextI18n")
                /* fun bindModel(movie: Data) {*/
        fun bindModel(film: Films) {
            Log.i(LOG_TAG, "TEST: bindModel() is called...")

            /*Picasso.get().load(movie.mPoster).into(poster)
            title.text = "Movie Name : " + movie.mTitle
            genre.text = "Movie Genre : " + movie.mGenre
            year.text = "Released Year : " + movie.mYear.toString()*/
            Picasso.get().load(film.moviePoster).into(poster)
            title.text = "Movie Name : " + film.movieTitle
            genre.text = "Movie Genre : " + film.movieGenre
            year.text = "Released Year : " + film.movieYear
        }
    }
}