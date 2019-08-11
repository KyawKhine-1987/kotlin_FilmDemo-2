package com.android.freelance.moviedemo.data.db.entity

import androidx.room.*

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(films: ArrayList<Films>)
    /*fun insert(data: Data)*/

    @Query("Select * from tbl_movies nolock;")
    fun fetchAll() : List<Films>

    @Query("Delete from tbl_movies;")
    fun deleteAllData()

    @Delete
    fun delete(films: Films)
}