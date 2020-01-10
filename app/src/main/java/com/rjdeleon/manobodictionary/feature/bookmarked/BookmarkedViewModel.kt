package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class BookmarkedViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = BookmarkedRepository(application)

    val bookmarkedEntries: LiveData<List<Entry>>
        get() = mRepository.bookmarkedEntries
            .flowOn(Dispatchers.IO)
            .asLiveData()
}
