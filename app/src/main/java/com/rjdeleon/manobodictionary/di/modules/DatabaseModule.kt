package com.rjdeleon.manobodictionary.di.modules

import android.content.Context
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import dagger.Module
import dagger.Provides

/**
 * Module for creating database
 */
@Module
class DatabaseModule(private val mContext: Context) {

    @Provides
    fun createDatabase(): DictionaryDatabase {
        return DictionaryDatabase.getDatabase(mContext.applicationContext)
    }

}