package com.rjdeleon.manobodictionary.di.modules

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val mViewModelStoreOwner: ViewModelStoreOwner) {

    @Provides
    fun createBookmarkedViewModel(): BookmarkedViewModel {
        return ViewModelProvider(mViewModelStoreOwner).get(BookmarkedViewModel::class.java)
    }
}