package com.android.freelance.moviedemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.freelance.moviedemo.data.db.entity.Films
import com.android.freelance.moviedemo.data.db.entity.FilmsDao

@Database(
    /*entities = arrayOf(Data::class),*/
    entities = [Films::class],
    version = 1,
    exportSchema = false
)

abstract class FilmsDatabase : RoomDatabase() {
    abstract fun dataDao(): FilmsDao

    companion object {
        @Volatile
        private var instance: FilmsDatabase? = null
        private val LOCK = Any()// LOCK is dummy object.

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FilmsDatabase::class.java, "Films.db"
            ).build()
        /*Room.inMemoryDatabaseBuilder(*/

        /* private var INSTANCE_DB: FilmsDatabase? = null
         fun getAppDataBase(context: Context): FilmsDatabase {
             val tempInstance = INSTANCE_DB
             if  (tempInstance != null){
                 return tempInstance
             }
             synchronized(this) {
                 val instance = Room.databaseBuilder(context.applicationContext,
                     FilmsDatabase::class.java,
                     "Movies.db").build()
                 INSTANCE_DB = instance
                 return instance
             }
         }*/

        fun destroyDataBase() {
            /*INSTANCE_DB = null*/
            instance = null
        }
    }
}