package com.rjdeleon.manobodictionary.data.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "note_set",
    indices = [Index("entry_id")],
    foreignKeys = [ForeignKey(
        entity = Entry::class,
        parentColumns = ["id"],
        childColumns = ["entry_id"],
        onDelete = CASCADE)])
data class NoteSet(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "id")
                   val id: Int,

                   @ColumnInfo(name = "entry_id")
                   var entryId: Int,

                   @ColumnInfo(name = "note_header")
                   val noteHeader: String,

                   @ColumnInfo(name = "note")
                   val note: String)