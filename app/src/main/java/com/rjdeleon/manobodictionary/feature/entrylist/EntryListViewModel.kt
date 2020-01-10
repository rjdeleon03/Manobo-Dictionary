package com.rjdeleon.manobodictionary.feature.entrylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class EntryListViewModel(application: Application,
                         letter: Char): AndroidViewModel(application) {

    private val mRepository = EntryListRepository(application, letter)

    val entries: LiveData<List<Entry>>
    get() = mRepository.entries
        .flowOn(Dispatchers.IO)
        .asLiveData()
}