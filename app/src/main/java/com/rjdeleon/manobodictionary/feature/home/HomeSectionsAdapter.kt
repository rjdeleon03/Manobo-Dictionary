package com.rjdeleon.manobodictionary.feature.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeSectionsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    fun addFragment(f: Fragment, ft: String) {
        mFragmentList.add(f)
        mFragmentTitleList.add(ft)
    }

    override fun getPageTitle(position: Int) = mFragmentTitleList[position]

    override fun getItem(position: Int) = mFragmentList[position]

    override fun getCount() = mFragmentList.size
}