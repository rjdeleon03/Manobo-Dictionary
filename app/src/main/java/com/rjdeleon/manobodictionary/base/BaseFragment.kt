package com.rjdeleon.manobodictionary.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI

abstract class BaseFragment : Fragment() {

    protected fun setupToolbar(toolbar: Toolbar, navController: NavController, listener: View.OnClickListener) {

        if (activity == null || activity !is AppCompatActivity) return

        val compatAct = activity as AppCompatActivity
        compatAct.setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(compatAct, navController)

        toolbar.setNavigationOnClickListener(listener)
    }
}