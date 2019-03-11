package com.rjdeleon.manobodictionary.feature.entry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class EntryViewModelFactory(private val application: Application,
                            private val entryId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EntryViewModel(application, entryId) as T
    }
}