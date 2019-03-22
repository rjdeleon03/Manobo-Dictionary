package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.Entry

@Suppress("SpellCheckingInspection")
@Dao
interface EntryDao {

    @Query("SELECT * FROM entry ORDER BY word")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT count(*) FROM entry")
    fun getCount(): Int

    @Query("SELECT count(*) FROM entry")
    fun getLiveCount(): LiveData<Int>


    @Query("SELECT * FROM entry WHERE word LIKE :letterFilter OR word LIKE :letterFilter2 COLLATE NOCASE")
    fun getByLetter(letterFilter: String,
                    letterFilter2: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getById(id: Int): LiveData<Entry>

    @Insert
    fun insert(entry: Entry)
}