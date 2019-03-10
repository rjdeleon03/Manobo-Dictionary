package com.rjdeleon.manobodictionary.feature.entrylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rjdeleon.manobodictionary.data.DictionaryDatabase

class EntryListRepository(application: Application,
                          letter: Char): AndroidViewModel(application) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)
    private val mEntries = mDatabase.entryDao().getByLetter("$letter%", "'$letter%")

    fun getEntries() = mEntries
}