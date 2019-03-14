package com.rjdeleon.manobodictionary.data.entities

import androidx.room.ColumnInfo

data class SearchResult(@ColumnInfo(name = "word")
                        var word: String = "",

                        @ColumnInfo(name = "part_of_speech")
                        var partOfSpeech: String = "",

                        @ColumnInfo(name = "meaning")
                        var meaning: String = "") {

    override fun toString(): String {
        return word
    }
}