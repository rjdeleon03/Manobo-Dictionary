package com.rjdeleon.manobodictionary.feature.entrylist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.data.entities.Entry
import java.lang.Exception

class EntryListAdapter(context: Context) :
    RecyclerView.Adapter<EntryListAdapter.EntryListViewHolder>() {

    private val mInflater = LayoutInflater.from(context)
    private lateinit var mEntries: List<Entry>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryListViewHolder {
        val view = mInflater.inflate(R.layout.item_entry_list, parent, false)
        return EntryListViewHolder(view)
    }

    override fun getItemCount(): Int =
        try {
            mEntries.size
        } catch(_: Exception) {
            0
        }

    override fun onBindViewHolder(holder: EntryListViewHolder, position: Int) {
        val entry = mEntries[position]
        holder.entryWordText.text = entry.word
    }

    fun setEntries(entries: List<Entry>) {
        mEntries = entries
        notifyDataSetChanged()
    }

    class EntryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val entryWordText = itemView.findViewById<TextView>(R.id.entryWordText)
    }
}