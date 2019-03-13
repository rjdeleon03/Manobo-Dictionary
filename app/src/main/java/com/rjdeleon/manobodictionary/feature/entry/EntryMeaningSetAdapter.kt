package com.rjdeleon.manobodictionary.feature.entry

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.base.BaseRecyclerViewAdapter
import com.rjdeleon.manobodictionary.data.entities.MeaningSet
import java.lang.Exception

class EntryMeaningSetAdapter(context: Context) :
    BaseRecyclerViewAdapter<EntryMeaningSetAdapter.EntryMeaningSetViewHolder>(context) {

    private lateinit var mMeaningSets: List<MeaningSet>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryMeaningSetViewHolder {
        val view = mInflater.inflate(R.layout.item_meaning_set, parent, false)
        return EntryMeaningSetViewHolder(view)
    }

    override fun getItemCount() =
        try {
            mMeaningSets.size
        } catch(_: Exception) {
            0
        }

    override fun onBindViewHolder(holder: EntryMeaningSetViewHolder, position: Int) {
        val meaningSet = mMeaningSets[position]
        holder.partOfSpeechText.text = meaningSet.partOfSpeech
        holder.definitionText.text = meaningSet.meaning
    }

    fun setMeaningSets(meaningSets: List<MeaningSet>) {
        mMeaningSets = meaningSets
        notifyDataSetChanged()
    }

    class EntryMeaningSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val partOfSpeechText = itemView.findViewById<TextView>(R.id.partOfSpeechText)!!
        val definitionText = itemView.findViewById<TextView>(R.id.definitionText)!!
    }
}

