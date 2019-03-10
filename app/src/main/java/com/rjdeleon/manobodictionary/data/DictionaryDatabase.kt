package com.rjdeleon.manobodictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rjdeleon.manobodictionary.data.dao.EntryDao
import com.rjdeleon.manobodictionary.data.dao.MeaningSetDao
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.data.entities.MeaningSet

@Database(entities = [Entry::class, MeaningSet::class],
    version = 1,
    exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    abstract fun meaningSetDao(): MeaningSetDao

    companion object {

        @Volatile
        private var INSTANCE: DictionaryDatabase? = null

        private const val DATABASE_NAME = "dictionary_database"

        fun getDatabase(context: Context): DictionaryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {

                /* Create database */
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}