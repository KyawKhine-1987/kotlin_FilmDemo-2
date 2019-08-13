package com.android.freelance.filmdemo.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//const val movie_NumberId = 0
const val builtIn_Id = 0
@Entity(tableName = "tbl_films")
data class Films(

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
){
    //It "primary key" can't input that Room DB bcoz it's json data has duplicated id="8666".
    @PrimaryKey(autoGenerate = true)
    var Id: Int = builtIn_Id
}
// "?= and @NonNull" is meant can never be null
// "!! and @Nullable" is meant can be null.
// @Ignore is meant solve this error "Ambiguous getter for Field"