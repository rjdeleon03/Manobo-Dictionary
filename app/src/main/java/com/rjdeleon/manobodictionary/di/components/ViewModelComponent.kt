package com.rjdeleon.manobodictionary.di.components

import com.rjdeleon.manobodictionary.di.modules.RepositoryModule
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedViewModel
import dagger.Component

/**
 * Responsible for injecting dependencies in a viewModel
 */
@Component(modules = [RepositoryModule::class])
interface ViewModelComponent {

    fun inject(viewModel: BookmarkedViewModel)
}