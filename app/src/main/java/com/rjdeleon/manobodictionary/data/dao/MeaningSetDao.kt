package com.rjdeleon.manobodictionary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.MeaningSet

@Dao
interface MeaningSetDao {

    @Query("SELECT * FROM meaning_set WHERE entry_id = :entryId")
    fun getByEntryId(entryId: Int)


    @Query("SELECT * FROM meaning_set")
    fun getAllMeaningSets()

    @Insert
    fun insert(meaningSet: MeaningSet)
}