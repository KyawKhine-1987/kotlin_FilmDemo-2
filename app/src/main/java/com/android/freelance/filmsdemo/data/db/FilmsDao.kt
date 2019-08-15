package com.android.freelance.filmdemo.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(films: ArrayList<Films>)

    @Query("Select * from tbl_films nolock order by Movie_Id;")
    fun fetchAll() : LiveData<List<Films>>

    @Query("Delete from tbl_films;")
    fun deleteAllData()

    /*@Delete
    fun delete(films: Films)*/
}