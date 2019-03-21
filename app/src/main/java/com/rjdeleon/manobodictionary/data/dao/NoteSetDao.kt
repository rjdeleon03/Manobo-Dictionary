package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.NoteSet

@Dao
interface NoteSetDao {

    @Query("SELECT * FROM note_set WHERE entry_id = :entryId")
    fun getByEntryId(entryId: Int) : LiveData<List<NoteSet>>

    @Query("SELECT * FROM note_set")
    fun getAllNoteSets() : LiveData<List<NoteSet>>

    @Insert
    fun insert(noteSet: NoteSet)
}