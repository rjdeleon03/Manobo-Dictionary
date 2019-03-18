package com.rjdeleon.manobodictionary.common.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.rjdeleon.manobodictionary.R
import kotlinx.android.synthetic.main.custom_search_view.view.*

class CustomSearchView(context: Context, attrs: AttributeSet)
    : LinearLayout(context, attrs) {

    enum class SearchViewState {
        DEFAULT,
        SEARCH,
        EMPTY
    }

    var toolbar: Toolbar?
    var searchFocusChangeListener: (() -> Unit)? = null
    var searchTextChangeListener: ((String) -> Unit)? = null
    private var cardLayoutParams: LinearLayout.LayoutParams
    private var cardMargin: Int? = null
    private var cardRadius: Float? = null
    private var cardElevation: Float? = null
    private var mState = SearchViewState.DEFAULT

    init {
        View.inflate(context, R.layout.custom_search_view, this)
        toolbar = cardToolbar

        /* Retrieve view attributes then set */
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView)
        searchView.queryHint = attributes.getString(R.styleable.CustomSearchView_searchHint)
        cardView.radius = attributes.getDimension(R.styleable.CustomSearchView_cardRadius, 0f)
        attributes.recycle()

        /* Retrieve card margins and radius */
        cardLayoutParams = cardView.layoutParams as LinearLayout.LayoutParams
        cardMargin = cardLayoutParams.topMargin
        cardElevation = cardView.cardElevation
        cardRadius = cardView.radius

        /* Set card view animation based on search view focus */
        searchView.setOnQueryTextFocusChangeListener { view, b ->
            if(b) {
                searchFocusChangeListener?.invoke()

                if (mState != SearchViewState.DEFAULT)
                    return@setOnQueryTextFocusChangeListener

                val a = object : Animation() {

                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        val margin = (cardMargin!! - cardMargin!! * interpolatedTime).toInt()
                        val radius = cardRadius!! - cardRadius!! * interpolatedTime
                        cardView.radius = radius
                        cardLayoutParams.setMargins(margin, margin, margin, margin)
                        cardView.layoutParams = cardLayoutParams
                    }
                }
                a.duration = 120
                mState = SearchViewState.SEARCH
                view.startAnimation(a)
            }
        }

        /* Listen to text changes in search view */
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchTextChangeListener?.invoke(
                    query ?: ""
                )
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchTextChangeListener?.invoke(
                    query ?: ""
                )
                return true
            }

        })
    }

    fun restoreSearchView() {
        when(mState) {
            SearchViewState.SEARCH -> {
                val a = object : Animation() {

                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        val margin = (cardMargin!! * interpolatedTime).toInt()
                        val radius = cardRadius!! * interpolatedTime
                        cardView.radius = radius
                        cardLayoutParams.setMargins(margin, margin, margin, margin)
                        cardView.layoutParams = cardLayoutParams

                        if (1f - interpolatedTime < 0.01) {
                            mState = SearchViewState.DEFAULT
                        }
                    }
                }
                a.duration = 120
                cardView.startAnimation(a)
            }
            SearchViewState.EMPTY -> {
                val a = object : Animation() {

                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        val margin = (cardMargin!! * interpolatedTime).toInt()
                        val radius = cardRadius!! * interpolatedTime
                        cardView.radius = radius
                        cardLayoutParams.setMargins(margin, margin, margin, margin)
                        cardView.layoutParams = cardLayoutParams
                        searchView.alpha = interpolatedTime

                        if (interpolatedTime < 0.01) {
                            toolbar?.navigationIcon
                                ?.setColorFilter(ContextCompat.getColor(context!!, android.R.color.black), PorterDuff.Mode.SRC_ATOP)
                            cardView.cardElevation = cardElevation!!
                            searchView.visibility = View.VISIBLE
                            cardView.setCardBackgroundColor(ContextCompat.getColor(context!!, android.R.color.white))
                        }

                        if (1f - interpolatedTime < 0.01) {
                            mState = SearchViewState.DEFAULT
                        }
                    }
                }
                a.duration = 120
                cardView.startAnimation(a)
            }
            else -> {}
        }
    }

    fun clearSearchView() {
        if (mState == SearchViewState.DEFAULT) {

            toolbar?.navigationIcon
                ?.setColorFilter(ContextCompat.getColor(context!!, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
            cardView.setCardBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
            cardView.cardElevation = 0f

            val a = object : Animation() {

                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    val margin = (cardMargin!! - cardMargin!! * interpolatedTime).toInt()
                    val radius = cardRadius!! - cardRadius!! * interpolatedTime
                    cardView.radius = radius
                    cardLayoutParams.setMargins(margin, margin, margin, margin)
                    cardView.layoutParams = cardLayoutParams
                    searchView.alpha = 1- interpolatedTime


                    if (1f - interpolatedTime < 0.01) {
                        searchView.visibility = View.GONE
                        mState = SearchViewState.EMPTY
                    }
                }
            }
            a.duration = 120
            cardView.startAnimation(a)
        }
    }
}