package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.Entry

@Suppress("SpellCheckingInspection")
@Dao
interface EntryDao {

    @Query("SELECT * FROM entry ORDER BY word")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT (SELECT count(*) FROM entry) + (SELECT count(*) FROM meaning_set) + (SELECT count(*) FROM note_set)")
    fun getLiveCount(): LiveData<Int>


    @Query("SELECT * FROM entry WHERE normalized_word LIKE :letterFilter COLLATE NOCASE")
    fun getByLetter(letterFilter: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry WHERE id = :id")
    fun getById(id: Int): LiveData<Entry>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEntries(entries: List<Entry>)
}