package com.rjdeleon.manobodictionary.feature.entrylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class EntryListViewModel(application: Application,
                         letter: Char): AndroidViewModel(application) {

    private val mRepository = EntryListRepository(application, letter)
    private val mEntries = mRepository.getEntries()

    fun getEntries() = mEntries
}