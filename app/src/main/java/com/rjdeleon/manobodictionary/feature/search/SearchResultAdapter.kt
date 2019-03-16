package com.rjdeleon.manobodictionary.feature.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.data.entities.SearchResult
import java.lang.Exception

class SearchResultAdapter(context: Context) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    private val mInflater = LayoutInflater.from(context)
    private var mResults: List<SearchResult>? = null

    fun setResults(results: List<SearchResult>) {
        mResults = results
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = mInflater.inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount() = try {
        mResults!!.size
    } catch (_: Exception) {
        0
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchResult = mResults!![position]
        holder.wordText.text = searchResult.word
        holder.meaningText.text = searchResult.meaning
        holder.setOnClickListener(View.OnClickListener {
            val action = SearchFragmentDirections
                .actionSearchFragmentToEntryFragment(searchResult.id)
            it.findNavController().navigate(action)
        })
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wordText = itemView.findViewById<TextView>(R.id.searchResultWordText)!!
        val meaningText = itemView.findViewById<TextView>(R.id.searchResultMeaningText)!!

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }

    }
}