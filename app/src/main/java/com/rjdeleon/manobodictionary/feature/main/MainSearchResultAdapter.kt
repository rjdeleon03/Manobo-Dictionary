package com.rjdeleon.manobodictionary.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.data.entities.SearchResult
import java.lang.Exception

class MainSearchResultAdapter(context: Context)
    : BaseAdapter(), Filterable {

    private val mInflater = LayoutInflater.from(context)
    private lateinit var mResults: List<SearchResult>

    fun setResults(results: List<SearchResult>) {
        mResults = results
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            val filterResults = FilterResults()
            override fun performFiltering(p0: CharSequence?): FilterResults {
                filterResults.values = mResults
                filterResults.count = mResults.size
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                try {
                    notifyDataSetChanged()
                } catch (_: Exception) {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    override fun getItem(position: Int) =
        try {
            mResults[position]
        } catch (_: Exception) {
            null
        }

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() =
        try {
            mResults.size
        } catch (_: Exception) {
            0
        }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = mInflater.inflate(R.layout.item_search_result, parent, false)
        view!!.findViewById<TextView>(R.id.searchResultWordText).text =
            mResults[position].word
        view.findViewById<TextView>(R.id.searchResultMeaningText).text =
            mResults[position].meaning
        return view
    }
}