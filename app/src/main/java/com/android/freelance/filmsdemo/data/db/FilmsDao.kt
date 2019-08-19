package com.android.freelance.filmdemo.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(films: ArrayList<Films>)//suspend

    @Query("select * from tbl_films nolock order by Movie_Id;")
    fun fetchAll(): LiveData<List<Films>>

    @Query("delete from tbl_films;")
    fun deleteData()//suspend

    /*@Query("select * from tbl_films nolock where Movie_Id in(select Movie_Id from tbl_films nolock where Movie_Id = :movieId);")
    fun ifExistsData(movieId: Int): List<Films>

    @Delete
    fun delete(films: Films)*/
}