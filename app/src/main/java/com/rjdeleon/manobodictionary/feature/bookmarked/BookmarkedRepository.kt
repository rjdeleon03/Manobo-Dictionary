package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import androidx.lifecycle.LiveData
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry

class BookmarkedRepository(application: Application) {

    private val mDatabase = DictionaryDatabase.getDatabase(application)

    private val mBookmarkedEntries = mDatabase.entryDao().getBookmarked()

    val bookmarkedEntries: LiveData<List<Entry>>
        get() = mBookmarkedEntries
}