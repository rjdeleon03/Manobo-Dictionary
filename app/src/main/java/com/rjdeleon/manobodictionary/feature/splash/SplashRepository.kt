package com.rjdeleon.manobodictionary.feature.splash

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.feature.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.nio.charset.Charset

@Suppress("SpellCheckingInspection")
class SplashRepository(application: Application) {

    private val mContext = application.applicationContext
    private val mDatabase = DictionaryDatabase.getDatabase(mContext)

    fun getLiveEntryCount(): LiveData<Int> = mDatabase.entryDao().getLiveCount()

    fun initializeEntries(progressAction: (Int) -> Unit,
                          completionAction: (Boolean) -> Unit) {
        CoroutineScope(Job() + Dispatchers.Main).launch(Dispatchers.IO) {
            seedData(progressAction, completionAction)
        }
    }

    @WorkerThread
    private fun seedData(progressAction: (Int) -> Unit,
                         completionAction: (Boolean) -> Unit) {

        try {

            /* Read JSON data from file */
            val gson = GsonBuilder().create()
            val inputStream = mContext.assets.open(Constants.SEED_FOLDER + Constants.SEED_FILENAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val charset = Charset.availableCharsets()["UTF-8"]!!
            val dictJson = String(buffer, charset)

            val listType = object: TypeToken<ArrayList<Entry>>(){}.type
            val entries = gson.fromJson<ArrayList<Entry>>(dictJson, listType)

            mDatabase.entryDao().insertAllEntries(entries)
            for(i in 0 .. (entries.size - 1)) {
                val entry = entries[i]

                if (!entry.meaningSets.isNullOrEmpty()) {
                    for (meaningSet in entry.meaningSets!!) {
                        meaningSet.entryId = entry.id
                    }
                    mDatabase.meaningSetDao().insertAllMeaningSets(entry.meaningSets!!)
                }

                if (!entry.noteSets.isNullOrEmpty()) {
                    for (noteSet in entry.noteSets!!) {
                        noteSet.entryId = entry.id
                    }
                    mDatabase.noteSetDao().insertAllNotes(entry.noteSets!!)
                }
                progressAction.invoke(i + 1)
            }
            completionAction.invoke(true)

        } catch (ex: Exception) {
            ex.printStackTrace()
            completionAction.invoke(false)
        }
    }
}