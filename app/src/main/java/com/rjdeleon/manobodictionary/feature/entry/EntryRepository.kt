package com.rjdeleon.manobodictionary.feature.entry

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase

class EntryRepository (application: Application,
                       entryId: Int) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)
    private val mEntry = mDatabase.entryDao().getByIdWithDetails(entryId)
    private val mMeaningSets = mDatabase.meaningSetDao().getByEntryId(entryId)
    private val mNoteSets = mDatabase.noteSetDao().getByEntryId(entryId)

    fun getEntry() = mEntry

    fun getMeaningSets() = mMeaningSets

    fun getNoteSets() = mNoteSets
}