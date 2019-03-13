package com.rjdeleon.manobodictionary.feature.entry


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.rjdeleon.manobodictionary.R
import kotlinx.android.synthetic.main.fragment_entry.*

/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EntryFragment : Fragment() {

    private val args: EntryFragmentArgs by navArgs()
    private lateinit var mViewModel: EntryViewModel
    private lateinit var mAdapter: EntryMeaningSetAdapter

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EntryFragment.
         */
        @JvmStatic
        fun newInstance() = EntryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Initialize viewModel and meaning set adapter */
        mViewModel = ViewModelProviders.of(this,
            EntryViewModelFactory(activity!!.application, args.entryId)).get(EntryViewModel::class.java)
        mAdapter = EntryMeaningSetAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryDefinitionRecyclerView.adapter = mAdapter

        mViewModel.getEntry().observe(this, Observer {
            entryWordText.text = it.word

            if (it.note.isEmpty() && it.relatedWord.isEmpty())
                return@Observer

            entryNoteGroup.visibility = View.VISIBLE
            entryNoteText.text = it.note
            entryRelatedWordText.text = it.relatedWord
        })
        mViewModel.getMeaningSets().observe(this, Observer{

            if (it.isEmpty()) return@Observer
            entryDefinitionGroup.visibility = View.VISIBLE
            mAdapter.setMeaningSets(it)
        })
    }
}
