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
   /* private val _spinner = MutableLiveData<Boolean>()
    private val _textviewNIC = MutableLiveData<String>()*/

    init {
        filmsRepository = FilmsRepository(application)
    }

    fun cancelParentJob() = parentJob.cancel()

    override fun onCleared() {
        Log.i(LOG_TAG, "TEST: onCleared() is called...")

        super.onCleared()
        cancelParentJob()
    }

    /*val spinner: LiveData<Boolean>
        get() = _spinner

    val textview_NIC: LiveData<String>
        get() = _textviewNIC*/

    fun insert(films: ArrayList<Films>) = scope.launch(Dispatchers.IO) {
        Log.i(LOG_TAG, "TEST: insert() is called...")

        filmsRepository!!.insert(films)
    }

    val fetchAllFilms: LiveData<List<Films>> = filmsRepository!!.fetchAllFilms

    /*fun refresh() {
        launchDataLoad {
            filmsRepository?.refresh()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: RefreshError) {
                _textviewNIC.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }*/
}