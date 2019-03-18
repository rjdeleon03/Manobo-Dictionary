package com.rjdeleon.manobodictionary.feature

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.rjdeleon.manobodictionary.MainNavDirections
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.feature.search.SharedSearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSharedSearchViewModel: SharedSearchViewModel
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Get nav controller */
        mNavController = findNavController(R.id.navigationFragment)

        /* Initialize shared search viewModel */
        mSharedSearchViewModel = ViewModelProviders.of(this).get(SharedSearchViewModel::class.java)

        val appBarConfig = AppBarConfiguration
            .Builder(setOf(R.id.homeFragment))
            .setDrawerLayout(mainDrawerLayout)
            .build()

        val toolbar = mainSearchView.toolbar
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(mNavController, appBarConfig)
        setupWithNavController(mainNavigationView, mNavController)
        mainNavigationView.menu.getItem(0).isChecked = true

        mNavController.addOnDestinationChangedListener { ct, dest, _ ->

            when(dest.id) {
                R.id.homeFragment -> {
                    toolbar?.setNavigationOnClickListener {
                        mainDrawerLayout.openDrawer(GravityCompat.START)
                    }

                    mainSearchView?.collapseSearchView()
                    currentFocus?.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                }
                else -> {
                    toolbar?.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }
            }
        }

        mainSearchView.searchFocusChangeListener = {
            if (mNavController.currentDestination?.id != R.id.searchFragment)
                mNavController.navigate(MainNavDirections.actionToSearchFragment())
        }

        mainSearchView.searchTextChangeListener = {query ->
            mSharedSearchViewModel.performSearch(query)
        }
    }

    override fun onBackPressed() {
        when(mNavController.currentDestination?.id) {
            R.id.homeFragment -> {
            }
            else -> {
            }
        }
        super.onBackPressed()
    }
}
