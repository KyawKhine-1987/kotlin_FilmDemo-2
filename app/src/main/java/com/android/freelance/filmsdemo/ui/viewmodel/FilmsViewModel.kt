package com.android.freelance.filmsdemo.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.freelance.filmdemo.data.db.entity.Films
import com.android.freelance.filmsdemo.data.db.FilmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FilmsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val LOG_TAG = FilmsViewModel::class.java.name

    // Coroutine Implementation Code lines
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    private var filmsRepository: FilmsRepository? = null

    init {
        filmsRepository = FilmsRepository(application)
    }

    private fun cancelParentJob() = parentJob.cancel()

    override fun onCleared() {
        Log.i(LOG_TAG, "TEST: onCleared() is called...")

        super.onCleared()
        cancelParentJob()
    }

    fun insert(films: ArrayList<Films>) = scope.launch(Dispatchers.IO) {
        Log.i(LOG_TAG, "TEST: insert() is called...")

        filmsRepository!!.insert(films)
    }

    val fetchAllFilms: LiveData<List<Films>> = filmsRepository!!.fetchAllFilms

    fun deleteAllData() = scope.launch(Dispatchers.IO){
        Log.i(LOG_TAG, "TEST: deleteAllData() is called...")

        filmsRepository!!.deleteAllData()
    }

    /*val checkData: List<Films> = filmsRepository!!.checkData*/
}