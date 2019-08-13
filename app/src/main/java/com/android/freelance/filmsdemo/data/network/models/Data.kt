package com.android.freelance.moviedemo.data.network.models

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("id")
    val mId: Int = 0,

    @SerializedName("title")
    val mTitle: String ?= null,

    @SerializedName("year")
    val mYear: Int = 0,

    @SerializedName("genre")
    val mGenre: String ?= null,

    @SerializedName("poster")
    val mPoster: String ?= null
)