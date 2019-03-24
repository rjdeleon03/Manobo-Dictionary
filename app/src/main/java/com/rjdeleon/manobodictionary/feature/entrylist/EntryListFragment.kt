package com.rjdeleon.manobodictionary.feature.entrylist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration

import com.rjdeleon.manobodictionary.databinding.FragmentEntryListBinding

private const val ARG_LETTER = "ARG_LETTER"

/**
 * A simple [Fragment] subclass.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EntryListFragment : Fragment() {

    private lateinit var mViewModel: EntryListViewModel
    private lateinit var mAdapter: EntryListAdapter
    private var mArgLetter = 'A'

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EntryListFragment.
         */
        @JvmStatic
        fun newInstance(letter: Char) = EntryListFragment().apply {
            arguments = Bundle().apply {
                putChar(ARG_LETTER, letter)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mArgLetter = it.getChar(ARG_LETTER)
        }

        /* Get viewModel */
        mViewModel = ViewModelProviders
            .of(this, EntryListViewModelFactory(activity!!.application, mArgLetter))
            .get(EntryListViewModel::class.java)

        /* Get adapter for entry list */
        mAdapter = EntryListAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentEntryListBinding.inflate(inflater, container, false)

        /* Set the viewModel, lifecycle owner, and adapter */
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.entryListRecyclerView.adapter = mAdapter
        binding.entryListRecyclerView
            .addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))

        return binding.root
    }
}
