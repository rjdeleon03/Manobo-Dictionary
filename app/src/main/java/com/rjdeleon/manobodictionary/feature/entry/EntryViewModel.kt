package com.rjdeleon.manobodictionary.feature.entry

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class EntryViewModel(application: Application,
                     entryId: Int): AndroidViewModel(application) {

    private val mRepository = EntryRepository(application, entryId)

    fun getEntry() = mRepository.getEntry()

    fun bookmarkEntry() = mRepository.bookmarkEntry()
}