package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.coroutines.flow.Flow

class BookmarkedRepository(application: Application) {

    private val mDatabase = DictionaryDatabase.getDatabase(application)

    private val mBookmarkedEntries = mDatabase.entryDao().getBookmarked()

    val bookmarkedEntries: Flow<List<Entry>>
        get() = mBookmarkedEntries
}