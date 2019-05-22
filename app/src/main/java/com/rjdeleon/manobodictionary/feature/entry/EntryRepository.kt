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
    private val mEntry = mDatabase.entryDao().getByIdWithDetails(entryId)

    fun getEntry() = mEntry

    fun bookmarkEntry(): Boolean {
        val entry = mEntry.value!!.entry!!
        val isSaved = !entry.isSaved
        entry.isSaved = isSaved

        CoroutineScope(Job() + Dispatchers.Main).launch (Dispatchers.IO) {
            mDatabase.entryDao().update(entry)
        }

        return isSaved
    }
}