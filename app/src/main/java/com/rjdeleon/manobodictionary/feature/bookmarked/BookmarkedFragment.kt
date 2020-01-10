package com.rjdeleon.manobodictionary.feature.bookmarked

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration

import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.databinding.FragmentBookmarkedBinding
import com.rjdeleon.manobodictionary.feature.entrylist.EntryListAdapter
import kotlinx.android.synthetic.main.fragment_bookmarked.*

class BookmarkedFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkedFragment()
    }

    private lateinit var mViewModel: BookmarkedViewModel
    private lateinit var mAdapter: EntryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = EntryListAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookmarkedListRecyclerView.adapter = mAdapter
        bookmarkedListRecyclerView.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(BookmarkedViewModel::class.java)

        val binding = FragmentBookmarkedBinding.bind(view!!)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}
