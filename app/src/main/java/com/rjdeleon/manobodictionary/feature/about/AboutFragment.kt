package com.rjdeleon.manobodictionary.feature.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.common.openUrlInBrowser
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frontMatterImage.setOnClickListener {
            openUrlInBrowser("https://philippines.sil.org/sites/phil/files/msm_front_matter.pdf") }
    }
}
