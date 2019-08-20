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


class FilmsAdapters(private val films: List<Films>) : RecyclerView.Adapter<FilmsAdapters.FilmsViewHolder>() {

    private val LOG_TAG = FilmsAdapters::class.java.name
    /*private val filmsDataList: MutableList<Films> = mutableListOf()*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() is called...")

        val layoutInflater = LayoutInflater.from(parent.context) //Gave me a hard time
        return FilmsViewHolder(layoutInflater.inflate(R.layout.item_films_list, parent, false))
    }

    /*override fun getItemCount(): Int {
        Log.i(LOG_TAG, "TEST: getItemCount() is called...")

        /*return filmsDataList.size*/
        return film.size
    }

    override fun getItemCount() =  filmsDataList.size*/

    override fun getItemCount() = films.size

    override fun onBindViewHolder(holder: FilmsViewHolder, i: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() is called...")

        /*holder.bindModel(filmsDataList[i])*/
        holder.bindModel(films[i])
    }

/* fun setFilms(dataList: List<Data>) {
     Log.i(LOG_TAG, "TEST: setFilms() is called...")

     filmsDataList.addAll(dataList)
     notifyDataSetChanged()
 }*/

    inner class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val LOG_TAG = FilmsViewHolder::class.java.name

        private val poster: ImageView = itemView.findViewById(R.id.ivFilmPoster)
        private val title: TextView = itemView.findViewById(R.id.tvFilmTitle)
        private val genre: TextView = itemView.findViewById(R.id.tvFilmGenre)
        private val year: TextView = itemView.findViewById(R.id.tvFilmYear)

        @SuppressLint("SetTextI18n")
                /* fun bindModel(film: Data) {*/
        fun bindModel(film: Films) {
            Log.i(LOG_TAG, "TEST: bindModel() is called...")

            /*Picasso.get().load(film.mPoster).into(poster)
            title.text = "film Name : " + film.mTitle
            genre.text = "film Genre : " + film.mGenre
            year.text = "Released Year : " + film.mYear.toString()*/
            Picasso.get().load(film.filmPoster).into(poster)
            title.text = "Film Name : " + film.filmTitle
            genre.text = "Film Genre : " + film.filmGenre
            year.text = "Released Year : " + film.filmYear
        }
    }
}