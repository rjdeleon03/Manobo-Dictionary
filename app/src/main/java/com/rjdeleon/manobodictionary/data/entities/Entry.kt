package com.rjdeleon.manobodictionary.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry (@PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "id")
                  val id: Int,

                  @ColumnInfo(name = "word")
                  val word: String,

                  @Ignore
                  val meaningSets: List<MeaningSet>,

                  @ColumnInfo(name = "note")
                  val note: String,

                  @ColumnInfo(name = "related_word")
                  val relatedWord: String)