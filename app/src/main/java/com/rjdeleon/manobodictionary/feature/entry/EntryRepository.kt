package com.rjdeleon.manobodictionary.feature.entry

import android.app.Application
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EntryRepository (application: Application,
                       entryId: Int) {

    private val mDatabase = DictionaryDatabase.getDatabase(application.applicationContext)
    private val mEntry = mDatabase.entryDao().getById(entryId)
    private val mMeaningSets = mDatabase.meaningSetDao().getByEntryId(entryId)
    private val mNoteSets = mDatabase.noteSetDao().getByEntryId(entryId)

    fun getEntry() = mEntry

    fun getMeaningSets() = mMeaningSets

    fun getNoteSets() = mNoteSets

    fun bookmarkEntry(willBookmark: Boolean) {
        val entry = mEntry.value!!
        entry.isSaved = willBookmark

        CoroutineScope(Job() + Dispatchers.Main).launch (Dispatchers.IO) {
            mDatabase.entryDao().update(entry)
        }
    }
}