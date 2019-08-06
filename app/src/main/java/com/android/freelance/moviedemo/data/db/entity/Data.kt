package com.android.freelance.moviedemo.data.db.entity

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("id")
    val mId: Int = 0,

    @SerializedName("title")
    val mTitle: String ?= null,

    @SerializedName("year")
    val mYear: String ?= null,

    @SerializedName("genre")
    val mGenre: String ?= null,

    @SerializedName("poster")
    val mPoster: String ?= null
)