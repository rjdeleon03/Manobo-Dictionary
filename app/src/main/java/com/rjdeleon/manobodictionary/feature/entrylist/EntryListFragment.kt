package com.rjdeleon.manobodictionary.feature.entrylist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.rjdeleon.manobodictionary.R

/**
 * A simple [Fragment] subclass.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EntryListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_list, container, false)
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
