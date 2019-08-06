package com.android.freelance.moviedemo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.moviedemo.R
import com.android.freelance.moviedemo.data.network.ApiMovies
import com.android.freelance.moviedemo.ui.activities.adapter.MoviesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name
    private lateinit var movieAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "TEST: onCreate() is called...")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieAdapter = MoviesAdapter()
        viewAdapter = movieAdapter
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.rvMoviesList).apply {

            setHasFixedSize(true) // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView

            layoutManager = viewManager // use a linear layout manager

            adapter = viewAdapter // specify an viewAdapter

            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
        /* rvMoviesList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
         rvMoviesList.adapter = movieAdapter*/

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiMovies = retrofit.create(ApiMovies::class.java)

        apiMovies.getMovies()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieAdapter.setMovies(it.data!!)
                /*var movieResponse: MovieResponse = it*/
            }, {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            })
    }
}
