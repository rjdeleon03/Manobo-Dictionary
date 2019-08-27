package com.rjdeleon.manobodictionary.feature.links


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rjdeleon.manobodictionary.R
import kotlinx.android.synthetic.main.fragment_links.*

/**
 * A simple [Fragment] subclass.
 */
class LinksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_links, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linkSil.setOnClickListener {
            openUrlInBrowser("https://philippines.sil.org/resources/online_resources/msm") }
        linkAlcadev.setOnClickListener {
            openUrlInBrowser("https://alcadev.wordpress.com/") }
        linkSos.setOnClickListener {
            openUrlInBrowser("https://saveourschoolsnetwork.wordpress.com/") }
        linkCpu.setOnClickListener {
            openUrlInBrowser("https://www.facebook.com/CPUnion/") }
    }

    private fun openUrlInBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
