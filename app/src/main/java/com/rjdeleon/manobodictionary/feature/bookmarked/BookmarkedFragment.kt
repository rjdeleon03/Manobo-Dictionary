package com.rjdeleon.manobodictionary.feature.bookmarked

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rjdeleon.manobodictionary.R

class BookmarkedFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkedFragment()
    }

    private lateinit var viewModel: BookmarkedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarked, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookmarkedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
