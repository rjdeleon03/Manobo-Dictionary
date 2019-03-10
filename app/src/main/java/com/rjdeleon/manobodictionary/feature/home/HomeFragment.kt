package com.rjdeleon.manobodictionary.feature.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.feature.entrylist.EntryListFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(homeViewPager)
        homeTabLayout.setupWithViewPager(homeViewPager)
    }

    private fun setupViewPager(vp: ViewPager) {
        val adapter = HomeSectionsAdapter(fragmentManager!!)

        var c = 'A'
        while (c <= 'Z') {
            adapter.addFragment(EntryListFragment.newInstance(), c.toString())
            ++c
        }
        vp.adapter = adapter
    }
}
