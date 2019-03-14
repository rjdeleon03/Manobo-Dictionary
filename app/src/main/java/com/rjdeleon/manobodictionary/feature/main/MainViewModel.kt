package com.rjdeleon.manobodictionary.feature.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rjdeleon.manobodictionary.data.entities.SearchResult

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = MainRepository(application)
    private val mSearchResults = MediatorLiveData<List<SearchResult>>()
    private var mSearchSource: LiveData<List<SearchResult>>? = null

    fun getSearchResults(): LiveData<List<SearchResult>> = mSearchResults

    fun performSearch(searchTerm: String) {
        if (mSearchSource != null) {
            mSearchResults.removeSource(mSearchSource!!)
        }
        mSearchSource = mRepository.getSearchResults(searchTerm)
        mSearchResults.addSource(mSearchSource!!) {
            mSearchResults.value = it
        }
    }

}