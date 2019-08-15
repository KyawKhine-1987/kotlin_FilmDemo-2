package com.android.freelance.filmsdemo.data.db

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.android.freelance.filmdemo.data.db.FilmsDatabase
import com.android.freelance.filmdemo.data.db.entity.Films
import com.android.freelance.filmdemo.data.db.entity.FilmsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmsRepository(application: Application) {

    private val LOG_TAG = FilmsRepository::class.java.name

    private var filmsDao: FilmsDao ?= null

    init {
        //createDatabase()
        val db = FilmsDatabase.invoke(application)
        filmsDao = db.filmsDao()
    }

    @WorkerThread
    suspend fun insert(films: ArrayList<Films>) {
        Log.i(LOG_TAG, "TEST: insert() is called...")

        filmsDao!!.insert(films)
    }

    val fetchAllFilms: LiveData<List<Films>> = filmsDao!!.fetchAll()

    /*suspend fun refresh() {
        withContext(Dispatchers.IO) {
            try {
               *//* val result = network.fetchNewWelcome().await()
                titleDao.insertTitle(Title(result))*//*
                filmsDao!!.fetchAll()
            } catch (error: NetworkException) {
                throw RefreshError(error)
            }
        }
    }*/
}

/*class RefreshError(cause: Throwable) : Throwable(cause.message, cause)

class NetworkException(message: String) : Throwable(message)*/
