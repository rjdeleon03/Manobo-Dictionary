package com.rjdeleon.manobodictionary.feature.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = SplashRepository(application)

    fun getLiveEntryCount() = mRepository.getLiveEntryCount()

    fun getTotalEntryCount() = mRepository.getTotalEntryCount()

    fun initializeEntries() = mRepository.initializeEntries()

}