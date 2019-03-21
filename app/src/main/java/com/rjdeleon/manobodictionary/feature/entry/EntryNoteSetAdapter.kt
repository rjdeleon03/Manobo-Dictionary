package com.rjdeleon.manobodictionary.feature.entry

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.base.BaseRecyclerViewAdapter
import com.rjdeleon.manobodictionary.data.entities.NoteSet
import java.lang.Exception

class EntryNoteSetAdapter(context: Context) :
    BaseRecyclerViewAdapter<EntryNoteSetAdapter.EntryNoteSetViewHolder>(context) {

    private lateinit var mNoteSets: List<NoteSet>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryNoteSetViewHolder {
        val view = mInflater.inflate(R.layout.item_meaning_set, parent, false)
        return EntryNoteSetViewHolder(view)
    }

    override fun getItemCount() =
        try {
            mNoteSets.size
        } catch(_: Exception) {
            0
        }

    override fun onBindViewHolder(holder: EntryNoteSetViewHolder, position: Int) {
        val noteSet = mNoteSets[position]
        holder.partOfSpeechText.text = noteSet.noteHeader
        holder.definitionText.text = noteSet.note
    }

    fun setNoteSets(meaningSets: List<NoteSet>) {
        mNoteSets = meaningSets
        notifyDataSetChanged()
    }

    class EntryNoteSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partOfSpeechText = itemView.findViewById<TextView>(R.id.partOfSpeechText)!!
        val definitionText = itemView.findViewById<TextView>(R.id.definitionText)!!
    }
}

