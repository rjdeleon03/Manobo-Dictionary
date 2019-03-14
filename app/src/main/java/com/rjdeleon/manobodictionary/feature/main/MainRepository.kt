package com.rjdeleon.manobodictionary.feature.main

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase

class MainRepository(application: Application) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)

    fun getSearchResults(searchTerm: String)
            = mDatabase.meaningSetDao().getSearchResults("%$searchTerm%")

}