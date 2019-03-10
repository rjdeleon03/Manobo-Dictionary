package com.rjdeleon.manobodictionary

import android.app.Application
import androidx.annotation.WorkerThread
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.rjdeleon.manobodictionary.data.DictionaryDatabase
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.coroutines.*
import java.lang.Exception
import java.nio.charset.StandardCharsets

class DictionaryApplication: Application() {

    private val SEED_FILENAME = "manobo_dict.json"
    private val SEED_FOLDER = "json/"


    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Job() + Dispatchers.Main).launch(Dispatchers.IO) {
            seedData()
        }
    }

    @WorkerThread
    private suspend fun seedData() {

        val db = DictionaryDatabase
            .getDatabase(this.applicationContext)

        if(db.entryDao().getCount() > 0) return

        val gson = GsonBuilder().create()

        var dictJson: String
        try {

            /* Read JSON data from file */
            val inputStream =
                applicationContext.assets.open(SEED_FOLDER + SEED_FILENAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            dictJson = String(buffer, StandardCharsets.UTF_8)

            val listType = object: TypeToken<ArrayList<Entry>>(){}.type
            val entries = gson.fromJson<ArrayList<Entry>>(dictJson, listType)

            for(i in 0 .. (entries.size - 1)) {
                val entry = entries[i]
                entry.id = i + 1
                db.entryDao().insert(entry)

                if (entry.meaningSets.isNullOrEmpty()) continue
                for(meaningSet in entry.meaningSets!!) {
                    if (!meaningSet.meaning.isBlank()
                        || !meaningSet.partOfSpeech.isBlank()) {
                        meaningSet.entryId = entry.id
                        db.meaningSetDao().insert(meaningSet)
                    }
                }
            }

            val x = db.entryDao().getCount()
            val y = 65

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}