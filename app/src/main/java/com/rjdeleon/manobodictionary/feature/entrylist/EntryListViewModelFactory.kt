package com.rjdeleon.manobodictionary.feature.entrylist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class EntryListViewModelFactory(private val application: Application,
                                private val letter: Char) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EntryListViewModel(application, letter) as T
    }
}