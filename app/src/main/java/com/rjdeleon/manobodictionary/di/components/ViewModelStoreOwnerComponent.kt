package com.rjdeleon.manobodictionary.di.components

import com.rjdeleon.manobodictionary.di.modules.ViewModelModule
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedFragment
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface ViewModelStoreOwnerComponent {

    fun inject(fragment: BookmarkedFragment)
}