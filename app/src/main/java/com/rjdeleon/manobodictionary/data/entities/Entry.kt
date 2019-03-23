package com.rjdeleon.manobodictionary.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "entry")
data class Entry (@PrimaryKey(autoGenerate = false)
                  @ColumnInfo(name = "id")
                  var id: Int = 0,

                  @ColumnInfo(name = "word")
                  var word: String = "",

                  @Ignore
                  @SerializedName("meaningSet")
                  var meaningSets: ArrayList<MeaningSet>? = null,

                  @Ignore
                  @SerializedName("noteSet")
                  var noteSets: ArrayList<NoteSet>? = null)