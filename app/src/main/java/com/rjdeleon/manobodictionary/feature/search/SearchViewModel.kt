package com.rjdeleon.manobodictionary.feature.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rjdeleon.manobodictionary.data.entities.SearchResult

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = SearchRepository(application)
    private val mSearchResults = MediatorLiveData<List<SearchResult>>()
    private var mSource: LiveData<List<SearchResult>>? = null

    fun getSearchResults(): LiveData<List<SearchResult>> = mSearchResults

    fun performSearch(searchTerm: String) {
        if (searchTerm.isBlank()) {
            mSearchResults.value = null
            return
        }

        if (mSource != null) {
            mSearchResults.removeSource(mSource!!)
        }
        mSource = mRepository.performSearch(searchTerm)
        mSearchResults.addSource(mSource!!) {
            mSearchResults.value = it
        }
    }

}