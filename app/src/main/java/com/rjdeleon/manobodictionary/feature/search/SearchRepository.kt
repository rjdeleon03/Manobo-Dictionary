package com.rjdeleon.manobodictionary.feature.search

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase

class SearchRepository(application: Application) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)

    fun performSearch(searchTerm: String) =
            mDatabase.meaningSetDao().getSearchResults("%$searchTerm%")
}