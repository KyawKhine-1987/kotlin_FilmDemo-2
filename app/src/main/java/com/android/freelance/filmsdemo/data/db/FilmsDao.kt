package com.android.freelance.filmdemo.data.db.entity

import androidx.room.*

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(films: ArrayList<Films>)
    /*fun insert(data: Data)*/

    @Query("Select * from tbl_films nolock;")
    fun fetchAll() : List<Films>

    @Query("Delete from tbl_films;")
    fun deleteAllData()

    @Delete
    fun delete(films: Films)
}