package com.android.freelance.filmdemo.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.filmdemo.R
import com.android.freelance.filmdemo.data.db.FilmsDatabase
import com.android.freelance.filmdemo.data.db.entity.Films
import com.android.freelance.filmdemo.data.network.ApiFilms
import kotlinx.android.synthetic.main.activity_main.*
import com.android.freelance.filmdemo.ui.activities.adapter.FilmsAdapters
import com.android.freelance.filmsdemo.ui.viewmodel.FilmsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name

    // ViewModel
    private lateinit var filmsViewModel: FilmsViewModel

    var progressBar: ProgressBar? = null
    var hasInternet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filmsViewModel = ViewModelProviders.of(this@MainActivity).get(FilmsViewModel::class.java)

        // ProgressBar
        progressBar = findViewById(R.id.pbLoadingIndicator)

        // display the progressbar
        progressBarLoading()

        bindingUIAndFetchDataFromNetwork()

        offlineData()
    }

    @SuppressLint("CheckResult")
    private fun bindingUIAndFetchDataFromNetwork() {
        Log.i(LOG_TAG, "TEST: bindingUIAndFetchDataFromNetwork() is called...")

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
                if (isNetworkAvailable()) {
                    hasInternet = true
                    /*movieAdapters.setFilms(lists)*/

                    // start some dummy thread that is different from UI thread
                    Thread(Runnable {

                        // performing some dummy time taking operation
                        val lists = it.data!!

                        val films = ArrayList<Films>()
                        for (list in lists) {
                            val film = Films()
                            film.filmId = list.mId
                            film.filmTitle = list.mTitle
                            film.filmYear = list.mYear
                            film.filmGenre = list.mGenre
                            film.filmPoster = list.mPoster
                            films.add(film)
                        }

                        filmsViewModel.deleteAllData()
                        filmsViewModel.insert(films)

                        progressBarGone()
                    }).start()
                }
            }, {
                if (!hasInternet) {
                    Toast.makeText(
                        applicationContext,
                        "No Internet Connection!\nThis is offline data which is taken to show you from the local storage.\nAlso " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                progressBarGone()
            })
    }

    private fun offlineData() {
        Log.i(LOG_TAG, "TEST: offlineData() is called...")

        filmsViewModel.fetchAllFilms.observe(this@MainActivity, Observer { films ->
            films?.let { refreshUIWith(films) }
        })
    }

    private fun refreshUIWith(films: List<Films>) {
        Log.i(LOG_TAG, "TEST: refreshUIWith() is called...")

        // try to touch View of UI thread
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            val filmsList = rvMoviesList
            val layoutManager = LinearLayoutManager(this)
            filmsList.layoutManager = layoutManager
            filmsList.hasFixedSize()
            filmsList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            val adapter = FilmsAdapters(films)
            filmsList.adapter = adapter
        })
    }

    private fun isNetworkAvailable(): Boolean {
        Log.i(LOG_TAG, "TEST: isNetworkAvailable() is called...")

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun progressBarLoading() {
        Log.i(LOG_TAG, "TEST: progressBarLoading() is called...")

        // when the task is started, make progressBar is loading
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            progressBar?.visibility = View.VISIBLE
        })
    }

    private fun progressBarGone() {
        Log.i(LOG_TAG, "TEST: progressBarLoading() is called...")

        // when the task was completed, make progressBar gone
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            progressBar?.visibility = View.GONE
        })
    }
}

/*
    // RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    // Adapter
    private lateinit var movieAdapters: FilmsAdapters

companion object {
  class MoviesTask internal constructor(context: MainActivity) : AsyncTask<List<Data>, Void, Void>() {

      private var mAsyncTaskDao: FilmsDao? = null

      override fun doInBackground(vararg data: List<Data>): Void? {

          mAsyncTaskDao?.insert(data[0])
          return null
      }
  }
}*/