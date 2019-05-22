package com.rjdeleon.manobodictionary.feature.entrylist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.base.BaseRecyclerViewAdapter
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.feature.bookmarked.BookmarkedFragmentDirections
import com.rjdeleon.manobodictionary.feature.home.HomeFragmentDirections
import java.lang.Exception

class EntryListAdapter(context: Context) :
    BaseRecyclerViewAdapter<EntryListAdapter.EntryListViewHolder>(context) {

    private lateinit var mEntries: List<Entry>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryListViewHolder {
        val view = mInflater.inflate(R.layout.item_entry_list, parent, false)
        return EntryListViewHolder(view)
    }

    override fun getItemCount() =
        try {
            mEntries.size
        } catch(_: Exception) {
            0
        }

    override fun onBindViewHolder(holder: EntryListViewHolder, position: Int) {
        val entry = mEntries[position]
        holder.entryWordText.text = entry.word
        holder.setOnClickListener(View.OnClickListener {
            val navController = it.findNavController()
            val action = when(navController.currentDestination!!.id) {
                R.id.homeFragment -> HomeFragmentDirections.actionHomeFragmentToEntryFragment(entry.id)
                else -> BookmarkedFragmentDirections.actionBookmarkedFragmentToEntryFragment(entry.id)
            }
            it.findNavController().navigate(action)
        })
    }

    fun setEntries(entries: List<Entry>) {
        mEntries = entries
        notifyDataSetChanged()
    }

    class EntryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val entryWordText = itemView.findViewById<TextView>(R.id.entryWordText)!!

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }
}