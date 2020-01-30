package com.rjdeleon.manobodictionary.di.modules

import android.app.Application
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedRepository
import dagger.Module
import dagger.Provides

/**
 * Module for creating repositories
 */
@Module
class RepositoryModule(private val mApplication: Application) {

    @Provides
    fun createBookmarkedRepository(): BookmarkedRepository {
        return BookmarkedRepository(mApplication)
    }
}