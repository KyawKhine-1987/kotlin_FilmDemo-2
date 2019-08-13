package com.android.freelance.moviedemo.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//const val movie_NumberId = 0

@Entity(tableName = "tbl_films")
data class Films(
    @PrimaryKey(autoGenerate = true)
    @NonNull // can never be null
    @ColumnInfo(name = "Movie_Id")
    var movieId: Int= 0,

    @ColumnInfo(name = "Movie_Title")
    var movieTitle: String? = null,

    @ColumnInfo(name = "Movie_Year")
    var movieYear: Int= 0,

    @ColumnInfo(name = "Movie_Genre")
    var movieGenre: String? = null,

    @ColumnInfo(name = "Movie_Poster")
    var moviePoster: String? = null
)
// @Nullable is meant can be null.
// @Ignore is meant solve this error "Ambiguous getter for Field"