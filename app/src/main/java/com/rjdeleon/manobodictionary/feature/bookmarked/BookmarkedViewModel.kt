package com.rjdeleon.manobodictionary.feature.bookmarked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.di.components.DaggerViewModelComponent
import com.rjdeleon.manobodictionary.di.modules.RepositoryModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BookmarkedViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var mRepository: BookmarkedRepository

    init {
        DaggerViewModelComponent
            .builder()
            .repositoryModule(RepositoryModule(application))
            .build()
            .inject(this)
    }

    val bookmarkedEntries: LiveData<List<Entry>>
        get(){
            return mRepository.bookmarkedEntries
            .flowOn(Dispatchers.IO)
            .asLiveData()
        }
}
