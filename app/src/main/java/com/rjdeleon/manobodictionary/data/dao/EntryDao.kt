package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.Entry

@Dao
interface EntryDao {

    @Query("SELECT * from entry ORDER BY word")
    fun getAllEntries(): LiveData<List<Entry>>

    @Insert
    fun insert(entry: Entry)
}