package com.rjdeleon.manobodictionary.data.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "meaning_set",
    indices = [Index("entry_id")],
    foreignKeys = [ForeignKey(
        entity = Entry::class,
        parentColumns = ["id"],
        childColumns = ["entry_id"],
        onDelete = CASCADE
    )]
)
data class MeaningSet(@PrimaryKey(autoGenerate = true)
                      @ColumnInfo(name = "id" )
                      val id: Int,

                      @ColumnInfo(name = "entry_id")
                      var entryId: Int,

                      @ColumnInfo(name = "part_of_speech")
                      val partOfSpeech: String,

                      @ColumnInfo(name = "meaning")
                      val meaning: String)