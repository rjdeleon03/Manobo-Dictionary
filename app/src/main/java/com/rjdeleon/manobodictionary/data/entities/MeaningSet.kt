package com.rjdeleon.manobodictionary.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "meaning_set",
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
                      val entryId: Int,

                      @ColumnInfo(name = "part_of_speech")
                      val partOfSpeech: String,

                      @ColumnInfo(name = "meaning")
                      val meaning: String)