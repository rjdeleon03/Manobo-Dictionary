package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rjdeleon.manobodictionary.data.entities.Entry

@Suppress("SpellCheckingInspection")
@Dao
interface EntryDao {

    @Query("SELECT * FROM entry ORDER BY word")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT count(*) FROM entry")
    fun getLiveCount(): LiveData<Int>


    @Query("SELECT * FROM entry WHERE normalized_word LIKE :letterFilter COLLATE NOCASE")
    fun getByLetter(letterFilter: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getById(id: Int): LiveData<Entry>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEntries(entries: List<Entry>)
}