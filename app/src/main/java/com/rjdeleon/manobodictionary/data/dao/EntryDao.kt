package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.Entry

@Dao
interface EntryDao {

    @Query("SELECT * FROM entry ORDER BY word")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT count(*) FROM entry")
    fun getCount(): Int


    @Query("SELECT * FROM entry WHERE word LIKE :letterFilter")
    fun getByLetter(letterFilter: String)

    @Insert
    fun insert(entry: Entry)
}