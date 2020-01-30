package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.di.components.DaggerRepositoryComponent
import com.rjdeleon.manobodictionary.di.modules.DatabaseModule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkedRepository(application: Application) {

    @Inject
    lateinit var mDatabase: DictionaryDatabase

    init {
        DaggerRepositoryComponent
            .builder()
            .databaseModule(DatabaseModule(application))
            .build()
            .inject(this)
    }

    val bookmarkedEntries: Flow<List<Entry>>
        get() = mDatabase.entryDao().getBookmarked()
}