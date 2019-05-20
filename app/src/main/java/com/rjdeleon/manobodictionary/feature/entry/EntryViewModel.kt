package com.rjdeleon.manobodictionary.feature.entry

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class EntryViewModel(application: Application,
                     entryId: Int): AndroidViewModel(application) {

    private val mRepository = EntryRepository(application, entryId)

    fun getEntry() = mRepository.getEntry()

    fun getMeaningSets() = mRepository.getMeaningSets()

    fun getNoteSets() = mRepository.getNoteSets()

    fun bookmarkEntry(willBookmark: Boolean) = mRepository.bookmarkEntry(willBookmark)
}