package com.android.freelance.moviedemo.data.network

import com.android.freelance.moviedemo.data.network.response.FilmsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiFilms {

    @GET("/filippella/Sample-API-Files/master/json/movies-api.json")
    fun getMovies(): Observable<FilmsResponse>
}