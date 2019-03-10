package com.rjdeleon.manobodictionary.feature.entrylist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.data.entities.Entry
import kotlinx.android.synthetic.main.fragment_entry_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EntryListFragment : Fragment() {

    private lateinit var mViewModel: EntryListViewModel
    private lateinit var mAdapter: EntryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Get viewModel */
        mViewModel = ViewModelProviders
            .of(this, EntryListViewModelFactory(activity!!.application, 'A'))
            .get(EntryListViewModel::class.java)

        /* Get adapter for entry list */
        mAdapter = EntryListAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryListRecyclerView.adapter = mAdapter
        mViewModel.getEntries().observe(this, Observer(mAdapter::setEntries))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EntryListFragment.
         */
        @JvmStatic
        fun newInstance() = EntryListFragment()
    }
}
