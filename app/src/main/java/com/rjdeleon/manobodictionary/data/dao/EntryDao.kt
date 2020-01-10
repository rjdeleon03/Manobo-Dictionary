package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.data.entities.EntryWithDetails
import kotlinx.coroutines.flow.Flow

@Suppress("SpellCheckingInspection")
@Dao
interface EntryDao {

    @Query("SELECT * FROM entry ORDER BY word")
    fun getAllEntries(): Flow<List<Entry>>

    @Query("SELECT (SELECT count(*) FROM entry) + (SELECT count(*) FROM meaning_set) + (SELECT count(*) FROM note_set)")
    fun getLiveCount(): LiveData<Int>

    @Query("SELECT * FROM entry WHERE is_saved = 1")
    fun getBookmarked(): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE normalized_word LIKE :letterFilter COLLATE NOCASE")
    fun getByLetter(letterFilter: String): LiveData<List<Entry>>

    @Transaction
    @Query("SELECT * FROM entry WHERE id = :id")
    fun getByIdWithDetails(id: Int): LiveData<EntryWithDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEntries(entries: List<Entry>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entry: Entry)
}