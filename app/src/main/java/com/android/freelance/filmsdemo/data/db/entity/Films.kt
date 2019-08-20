package com.android.freelance.filmdemo.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

//const val movie_NumberId = 0
const val builtIn_Id = 0
@Entity(tableName = "tbl_films")
data class Films(

    @ColumnInfo(name = "Film_Id")
    //@Ignore
    var filmId: Int= 0,

    @ColumnInfo(name = "Film_Title")
    var filmTitle: String? = null,

    @ColumnInfo(name = "Film_Year")
    var filmYear: Int= 0,

    @ColumnInfo(name = "Film_Genre")
    var filmGenre: String? = null,

    @ColumnInfo(name = "Film_Poster")
    var filmPoster: String? = null
){
    //It "primary key" can't input that Room DB bcoz it's json data has duplicated id="8666".
    @PrimaryKey(autoGenerate = true)
    var id: Int = builtIn_Id
}
// "?= and @NonNull" is meant can never be null
// "!! and @Nullable" is meant can be null.
// @Ignore is meant solve this error "Ambiguous getter for Field"