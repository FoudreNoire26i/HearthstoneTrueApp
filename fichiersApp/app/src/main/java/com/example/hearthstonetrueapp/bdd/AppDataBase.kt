package com.example.hearthstonetrueapp.bdd

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = arrayOf(MyCards::class), version = 1, exportSchema = false)
abstract class AppDataBase() : RoomDatabase() {
    //abstract fun myCardsDaoOld() : MyCardsDao

    // --- SINGLETON ---
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // --- INSTANCE ---
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "cards_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                /*INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.myCardsDao())
                    }
                }*/
            }

            suspend fun populateDatabase(wordDao: MyCardsDao) {
                // Start the app with a clean database every time.
                // Not needed if you only populate on creation.
                //wordDao.deleteAll()
                wordDao.insertCard(MyCards(json = "Ben"))
                wordDao.insertCard(MyCards(json = "Mat"))
                wordDao.insertCard(MyCards(json = "Lmr"))
            }
        }

    }


    // --- DAO ---
    abstract fun myCardsDao(): MyCardsDao



}