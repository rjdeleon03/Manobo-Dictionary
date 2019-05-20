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

                  @ColumnInfo(name = "normalized_word")
                  var normalizedWord: String = "",

                  @ColumnInfo(name = "is_saved")
                  var isSaved: Boolean = false,

                  @Ignore
                  @SerializedName("meaningSet")
                  var meaningSets: ArrayList<MeaningSet>? = null,

                  @Ignore
                  @SerializedName("noteSet")
                  var noteSets: ArrayList<NoteSet>? = null)