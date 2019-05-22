package com.rjdeleon.manobodictionary.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
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

        mainBackgroundScrollView.setOnTouchListener(object: View.OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        /* Get nav controller */
        mNavController = findNavController(R.id.navigationFragment)

        /* Initialize shared search viewModel */
        mSharedSearchViewModel = ViewModelProviders.of(this).get(SharedSearchViewModel::class.java)

        val appBarConfig = AppBarConfiguration
            .Builder(setOf(
                R.id.homeFragment,
                R.id.bookmarkedFragment,
                R.id.aboutFragment))
            .setDrawerLayout(mainDrawerLayout)
            .build()

        val toolbar = mainSearchView.toolbar
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(mNavController, appBarConfig)
        setupWithNavController(mainNavigationView, mNavController)
        mainNavigationView.menu.getItem(0).isChecked = true
        mainNavigationView.menu.findItem(R.id.menuSil).setOnMenuItemClickListener {
            openUrlInBrowser("https://philippines.sil.org/resources/online_resources/msm")
            true
        }
        mainNavigationView.menu.findItem(R.id.menuSos).setOnMenuItemClickListener {
            openUrlInBrowser("https://saveourschoolsnetwork.wordpress.com/")
            true
        }

        mNavController.addOnDestinationChangedListener { _, dest, _ ->

            when(dest.id) {
                R.id.aboutFragment,
                R.id.bookmarkedFragment,
                R.id.homeFragment -> {
                    toolbar?.setNavigationOnClickListener {
                        mainDrawerLayout.openDrawer(GravityCompat.START)
                    }

                    mainSearchView?.switchToDefaultState()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    currentFocus?.clearFocus()
                }
                R.id.entryFragment -> {
                    mainSearchView?.switchToEmptyState()
                    toolbar?.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }
                R.id.searchFragment -> {
                    mainSearchView?.switchToSearchState()
                    toolbar?.setNavigationOnClickListener {
                        onBackPressed()
                    }
                }
            }
        }

        mainSearchView.searchFocusChangeListener = {
            if (mNavController.currentDestination?.id != R.id.searchFragment) {
                mNavController.navigate(MainNavDirections.actionToSearchFragment())
            }
        }

        mainSearchView.searchTextChangeListener = {query ->
            mSharedSearchViewModel.performSearch(query)
        }
    }

    private fun openUrlInBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}
