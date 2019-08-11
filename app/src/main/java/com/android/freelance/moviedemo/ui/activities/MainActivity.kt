package com.android.freelance.moviedemo.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.moviedemo.R
import com.android.freelance.moviedemo.data.db.FilmsDatabase
import com.android.freelance.moviedemo.data.db.entity.Films
import com.android.freelance.moviedemo.data.network.ApiFilms
import kotlinx.android.synthetic.main.activity_main.*
import com.android.freelance.moviedemo.ui.activities.adapter.FilmsAdapters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name

    // RecyclerView
    /*private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>*/
    private lateinit var viewManager: RecyclerView.LayoutManager

    // Adapter
    private lateinit var movieAdapters: FilmsAdapters

    // database
    internal var db: FilmsDatabase? = null
    lateinit var film: List<Films>


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiMovies = retrofit.create(ApiFilms::class.java)

        apiMovies.getMovies()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                /*movieAdapters.setMovies(lists)*/
                Thread {
                    val lists = it.data!!

                    val films = ArrayList<Films>()
                    for (list in lists) {
                        val film = Films()
                        film.movieId = list.mId
                        film.movieTitle = list.mTitle
                        film.movieYear = list.mYear
                        film.movieGenre = list.mGenre
                        film.moviePoster = list.mPoster
                        films.add(film)
                    }
                    db?.dataDao()?.insert(films)
                    db?.dataDao()?.fetchAll()
                    this@MainActivity.runOnUiThread(Runnable {
                        val filmList = rvMoviesList
                        val layoutManager = LinearLayoutManager(this)
                        filmList.layoutManager = layoutManager
                        filmList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                        val adapter = FilmsAdapters(films)
                        filmList.adapter = adapter
                    })//refreshUIWith(filmDataList!!)
                }.start()
            }, {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
    }


    private fun initialize() {
        Log.i(LOG_TAG, "TEST: initialize() is called...")

        //createDatabase()
        db = FilmsDatabase.invoke(applicationContext)
    }
}

/*    private fun refreshUIWith(films: List<Films>) {
        Log.i(LOG_TAG, "TEST: refreshUIWith() is called...")

        val filmList = rvMoviesList
        val layoutManager = LinearLayoutManager(this)
        filmList.layoutManager = layoutManager
        filmList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val adapter = FilmsAdapters(films)
        filmList.adapter = adapter
    }

companion object {
  class MoviesTask internal constructor(context: MainActivity) : AsyncTask<List<Data>, Void, Void>() {

      private var mAsyncTaskDao: FilmsDao? = null

      override fun doInBackground(vararg data: List<Data>): Void? {

          mAsyncTaskDao?.insert(data[0])
          return null
      }
  }
}*/