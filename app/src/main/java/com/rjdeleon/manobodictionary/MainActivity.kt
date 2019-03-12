package com.rjdeleon.manobodictionary

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
