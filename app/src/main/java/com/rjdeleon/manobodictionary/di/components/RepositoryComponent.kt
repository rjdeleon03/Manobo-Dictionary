package com.rjdeleon.manobodictionary.di.components

import com.rjdeleon.manobodictionary.di.modules.DatabaseModule
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedRepository
import dagger.Component

/**
 * Responsible for injecting dependencies in a repository
 */
@Component(modules = [DatabaseModule::class])
interface RepositoryComponent {

    fun inject(repository: BookmarkedRepository)
}