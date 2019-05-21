package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rjdeleon.manobodictionary.data.entities.Entry

class BookmarkedViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = BookmarkedRepository(application)

    val bookmarkedEntries: LiveData<List<Entry>>
        get() = mRepository.bookmarkedEntries
}
