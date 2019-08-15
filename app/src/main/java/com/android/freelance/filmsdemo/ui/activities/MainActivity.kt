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

    var hasInternet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filmsViewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)

        /*// ProgressBar
        val progressBar: ProgressBar = findViewById(R.id.pbLoadingIndicator)

        // TextView
        val textview_nic: TextView = findViewById(R.id.tvNIC)

        filmsViewModel.refresh()

        filmsViewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                progressBar.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        filmsViewModel.textview_NIC.observe(this, Observer { value ->
            value?.let {
                textview_nic.text = it
            }
        })*/

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
                    /*movieAdapters.setMovies(lists)*/

                    // start some dummy thread that is different from UI thread
                    Thread(Runnable {

                        // performing some dummy time taking operation
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

                        filmsViewModel.insert(films)
                    }).start()
                }
            }, {
                /* Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()*/
                if (!hasInternet) {
                    Toast.makeText(
                        applicationContext,
                        "No Internet Connection!\nSo, it doesn't take the data from https://raw.githubusercontent.com.\nAlso " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun refreshUIWith(films: List<Films>) {
        Log.i(LOG_TAG, "TEST: refreshUIWith() is called...")

        // try to touch View of UI thread
        this@MainActivity.runOnUiThread(Runnable {
            val filmList = rvMoviesList
            val layoutManager = LinearLayoutManager(this)
            filmList.layoutManager = layoutManager
            filmList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            val adapter = FilmsAdapters(films)
            filmList.adapter = adapter
        })
    }

    private fun offlineData() {
        Log.i(LOG_TAG, "TEST: offlineData() is called...")

        filmsViewModel.fetchAllFilms.observe(this@MainActivity, Observer { films ->
            films?.let { refreshUIWith(films) }
        })
    }

    private fun isNetworkAvailable(): Boolean {
        Log.i(LOG_TAG, "TEST: isNetworkAvailable() is called...")

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected
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