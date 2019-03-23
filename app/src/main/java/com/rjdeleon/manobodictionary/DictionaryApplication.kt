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
}