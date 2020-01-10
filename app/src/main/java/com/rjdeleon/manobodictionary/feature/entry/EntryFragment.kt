package com.rjdeleon.manobodictionary.feature.entry


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.common.observeAllButSeparateFirstUpdate

import com.rjdeleon.manobodictionary.databinding.FragmentEntryBinding
import kotlinx.android.synthetic.main.fragment_entry.*
import kotlinx.android.synthetic.main.fragment_entry.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [EntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EntryFragment : Fragment() {

    private val args: EntryFragmentArgs by navArgs()
    private lateinit var mViewModel: EntryViewModel
    private lateinit var mMeaningSetAdapter: EntryMeaningSetAdapter
    private lateinit var mNoteSetAdapter: EntryNoteSetAdapter
    private lateinit var mBookmarkButton: MenuItem

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
        mMeaningSetAdapter = EntryMeaningSetAdapter(context!!)
        mNoteSetAdapter = EntryNoteSetAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        /* Set adapters */
        view.entryDefinitionRecyclerView.adapter = mMeaningSetAdapter
        view.entryNoteRecyclerView.adapter = mNoteSetAdapter

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this,
            EntryViewModelFactory(activity!!.application, args.entryId)).get(EntryViewModel::class.java)

        /* Set viewModel, lifecycle owner*/
        val binding = FragmentEntryBinding.bind(view!!)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /* Observe LiveData from viewModel */
        mViewModel.getEntry().observeAllButSeparateFirstUpdate(viewLifecycleOwner, Observer {
            val isBookmarked = it.entry!!.isSaved
            setBookmarkButtonState(isBookmarked)
            if (isBookmarked) {
                Snackbar.make(entryCoordinatorLayout, R.string.entry_bookmark_saved, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(entryCoordinatorLayout, R.string.entry_bookmark_removed, Snackbar.LENGTH_SHORT).show()
            }
        }) {
            setBookmarkButtonState (it.entry!!.isSaved)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.entry_menu, menu)

        mBookmarkButton = menu.findItem(R.id.entryMenuBookmark)
        mBookmarkButton.setOnMenuItemClickListener {
            mViewModel.bookmarkEntry()
            true
        }
    }

    private fun setBookmarkButtonState(isBookmarked: Boolean) {
        if (isBookmarked) {
            mBookmarkButton.setIcon(R.drawable.ic_bookmark_24dp)
        } else {
            mBookmarkButton.setIcon(R.drawable.ic_bookmark_border_24dp)
        }
    }
}
