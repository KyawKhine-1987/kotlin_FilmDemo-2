package com.android.freelance.filmdemo.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.freelance.filmdemo.data.db.entity.Films
import com.android.freelance.filmdemo.data.db.entity.FilmsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    /*entities = arrayOf(Data::class),*/
    entities = [Films::class],
    version = 1,
    exportSchema = false
)

abstract class FilmsDatabase : RoomDatabase() {



    abstract fun filmsDao(): FilmsDao

    companion object {
        @Volatile
        private var instance: FilmsDatabase? = null
        private val LOCK = Any()// LOCK is dummy object.

        private val LOG_TAG = FilmsDatabase::class.java.name

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            Log.i(LOG_TAG, "TEST: invoke() called...")

            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FilmsDatabase::class.java, "Films"
            ).build()


        /*private class DeleteEntityInDatabase(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                instance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        database.filmsDao().deleteAllData()
                    }
                }
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
            }
        }*/
    }
}

//FilmsDatabase::class.java, "Films.db"
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
 }
 INSTANCE_DB = null*/