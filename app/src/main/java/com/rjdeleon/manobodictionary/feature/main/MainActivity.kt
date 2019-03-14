package com.rjdeleon.manobodictionary.feature.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.rjdeleon.manobodictionary.MainNavDirections
import com.rjdeleon.manobodictionary.R
import com.rjdeleon.manobodictionary.feature.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MainSearchResultAdapter
    private lateinit var mViewModel: MainViewModel
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavController = findNavController(R.id.navigationFragment)

        mAdapter = MainSearchResultAdapter(this, object: OnItemClickListener {
            override fun onItemClick(entryId: Int) {
                val action = MainNavDirections.actionToEntryFragment(entryId)
                mNavController.navigate(action)
                clearViewFocus(searchTextField)
            }
        })

        mViewModel = MainViewModel(application)
        mViewModel.getSearchResults().observe(this, Observer(mAdapter::setResults))

        searchTextField.setAdapter(mAdapter)
        searchTextField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                mViewModel.performSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    /**
     * Clears search text field focus when touching outside of input edit text
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    clearViewFocus(v)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Clears search text field focus when back button is pressed
     */
    override fun onBackPressed() {
        super.onBackPressed()
        if (searchTextField.isFocused)
            clearViewFocus(searchTextField)
    }

    /**
     * Clears focus of a view
     */
    private fun clearViewFocus(v: View) {
        v.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
