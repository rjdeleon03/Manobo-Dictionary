package com.rjdeleon.manobodictionary.data.entities

import androidx.room.Embedded
import androidx.room.Relation

class EntryWithDetails {

    @Embedded
    var entry: Entry? = null

    @Relation(parentColumn = "id", entityColumn = "entry_id", entity = MeaningSet::class)
    var meaningSets: List<MeaningSet>? = null

    @Relation(parentColumn = "id", entityColumn = "entry_id", entity = NoteSet::class)
    var noteSets: List<NoteSet>? = null

}