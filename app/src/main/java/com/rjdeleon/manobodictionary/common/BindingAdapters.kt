package com.rjdeleon.manobodictionary.common

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.data.entities.Entry
import com.rjdeleon.manobodictionary.data.entities.MeaningSet
import com.rjdeleon.manobodictionary.data.entities.SearchResult
import com.rjdeleon.manobodictionary.feature.entry.EntryMeaningSetAdapter
import com.rjdeleon.manobodictionary.feature.entrylist.EntryListAdapter
import com.rjdeleon.manobodictionary.feature.search.SearchResultAdapter

@BindingAdapter("wordBasedVisibility")
fun setWordBasedVisibility(v: View, e: Entry?) {
    if (e == null) return
    if (!e.relatedWord.isEmpty() || !e.note.isEmpty()) {
        v.visibility = View.VISIBLE
        return
    }
    v.visibility = View.GONE
}

@BindingAdapter("defnBasedVisibility")
fun setDefnBasedVisibility(v: View, ms: List<MeaningSet>?) {
    if (!ms.isNullOrEmpty()) {
        v.visibility = View.VISIBLE
        return
    }
    v.visibility = View.GONE
}

@BindingAdapter("hidingText")
fun setHidingText(tv: TextView, str: String?) {
    if (str.isNullOrEmpty()) {
        tv.visibility = View.GONE
        return
    }
    tv.visibility = View.VISIBLE
    tv.text = str
}

@BindingAdapter("entryList")
fun setEntries(rv: RecyclerView, e: List<Entry>?) {
    if (e.isNullOrEmpty()) return

    val adapter = rv.adapter
    if (adapter is EntryListAdapter) {
        adapter.setEntries(e)
    }
}

@BindingAdapter("meaningSets")
fun setMeaningSets(rv: RecyclerView, ms: List<MeaningSet>?) {
    if (ms.isNullOrEmpty()) return

    val adapter = rv.adapter
    if (adapter is EntryMeaningSetAdapter) {
        adapter.setMeaningSets(ms)
    }
}

@BindingAdapter("searchResults")
fun setSearchResults(rv: RecyclerView, sr: List<SearchResult>?) {

    val adapter = rv.adapter
    if (adapter is SearchResultAdapter) {
        if (sr.isNullOrEmpty()) {
            adapter.setResults(ArrayList())
            return
        }
        adapter.setResults(sr)
    }
}