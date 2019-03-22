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

class SplashRepository(application: Application) {

    private val mContext = application.applicationContext
    private val mDatabase = DictionaryDatabase.getDatabase(mContext)
    private val mLiveEntryCount = mDatabase.entryDao().getLiveCount()
    private val mTotalEntryCount: MutableLiveData<Int> = MutableLiveData()

    fun getLiveEntryCount(): LiveData<Int> = mLiveEntryCount

    fun getTotalEntryCount(): LiveData<Int> = mTotalEntryCount

    fun initializeEntries() {
        CoroutineScope(Job() + Dispatchers.Main).launch(Dispatchers.IO) {
            seedData()
        }
    }

    @WorkerThread
    private fun seedData() {

        val count = mDatabase.entryDao().getCount()
        if(count >= 5630) {
            mTotalEntryCount.postValue(count)
            return
        }

        val gson = GsonBuilder().create()
        try {

            /* Read JSON data from file */
            val inputStream = mContext.assets.open(Constants.SEED_FOLDER + Constants.SEED_FILENAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val charset = Charset.availableCharsets()["UTF-8"]!!
            val dictJson = String(buffer, charset)

            val listType = object: TypeToken<ArrayList<Entry>>(){}.type
            val entries = gson.fromJson<ArrayList<Entry>>(dictJson, listType)
            mTotalEntryCount.postValue(entries.size)

            for(i in 0 .. (entries.size - 1)) {
                val entry = entries[i]
                entry.id = i + 1
                mDatabase.entryDao().insert(entry)

                if (!entry.meaningSets.isNullOrEmpty()) {
                    for (meaningSet in entry.meaningSets!!) {
                        if (!meaningSet.meaning.isBlank()
                            || !meaningSet.partOfSpeech.isBlank()
                        ) {
                            meaningSet.entryId = entry.id
                            mDatabase.meaningSetDao().insert(meaningSet)
                        }
                    }
                }

                if (!entry.noteSets.isNullOrEmpty()) {
                    for (noteSet in entry.noteSets!!) {
                        if (!noteSet.noteHeader.isBlank()
                            || !noteSet.note.isBlank()
                        ) {
                            noteSet.entryId = entry.id
                            mDatabase.noteSetDao().insert(noteSet)
                        }
                    }
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}