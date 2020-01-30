package com.rjdeleon.manobodictionary.feature.entrylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.coroutines.flow.Flow

class EntryListRepository(application: Application,
                          letter: Char) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)
    private val mEntries = mDatabase.entryDao().getByLetter("$letter%")

    val entries: Flow<List<Entry>>
        get() = mEntries
}