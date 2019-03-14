package com.rjdeleon.manobodictionary.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rjdeleon.manobodictionary.data.entities.MeaningSet
import com.rjdeleon.manobodictionary.data.entities.SearchResult

@Dao
interface MeaningSetDao {

    @Query("SELECT * FROM meaning_set WHERE entry_id = :entryId")
    fun getByEntryId(entryId: Int) : LiveData<List<MeaningSet>>

    @Query("SELECT * FROM meaning_set")
    fun getAllMeaningSets() : LiveData<List<MeaningSet>>

    @Query("SELECT entry_id, word, part_of_speech, meaning " +
            "FROM meaning_set JOIN entry ON meaning_set.entry_id = entry.id " +
            "WHERE word LIKE :searchTerm or " +
            "part_of_speech LIKE :searchTerm or " +
            "meaning LIKE :searchTerm")
    fun getSearchResults(searchTerm: String) : LiveData<List<SearchResult>>

    @Insert
    fun insert(meaningSet: MeaningSet)
}