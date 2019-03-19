package com.rjdeleon.manobodictionary.common.views

import android.content.Context
import android.graphics.PorterDuff
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

    var toolbar: Toolbar?
    var searchFocusChangeListener: (() -> Unit)? = null
    var searchTextChangeListener: ((String) -> Unit)? = null
    private var cardLayoutParams: LinearLayout.LayoutParams
    private var cardMargin: Int? = null
    private var cardRadius: Float? = null
    private var cardElevation: Float? = null

    private fun getCardMargin() = (cardView.layoutParams as LinearLayout.LayoutParams).topMargin
    private fun getCardRadius() = cardView.radius

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
        searchView.setOnQueryTextFocusChangeListener { _, b ->
            if(b) {
                searchFocusChangeListener?.invoke()
                switchToSearchState()
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

    fun switchToDefaultState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = !isSearchBarVisible()
        val marginUpdateNeeded = currCardMargin == 0 && currCardRadius.isAlmostZero()
        if (!marginUpdateNeeded && !searchBarUpdateNeeded)
        {
            return
        }

        setSearchBarState(true)

        val a = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (marginUpdateNeeded) {
                    val margin = (cardMargin!! * interpolatedTime).toInt()
                    val radius = cardRadius!! * interpolatedTime
                    cardView.radius = radius
                    cardLayoutParams.setMargins(margin, margin, margin, margin)
                    cardView.layoutParams = cardLayoutParams
                }

                if (searchBarUpdateNeeded) {
                    searchView.alpha = interpolatedTime
                }

                if ((1f - interpolatedTime).isAlmostZero()) {
                    searchView.visibility = View.VISIBLE
                }
            }
        }
        a.duration = 120
        cardView.startAnimation(a)
    }

    fun switchToSearchState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = !isSearchBarVisible()
        val marginUpdateNeeded = currCardMargin > 0 && !currCardRadius.isAlmostZero()
        if (!marginUpdateNeeded && !searchBarUpdateNeeded)
        {
            return
        }

        setSearchBarState(true)

        val a = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (marginUpdateNeeded) {
                    val margin = (currCardMargin - currCardMargin * interpolatedTime).toInt()
                    val radius = currCardRadius - currCardRadius * interpolatedTime
                    cardView.radius = radius
                    cardLayoutParams.setMargins(margin, margin, margin, margin)
                    cardView.layoutParams = cardLayoutParams
                }

                if (searchBarUpdateNeeded) {
                    searchView.alpha = interpolatedTime
                }

                if ((1f - interpolatedTime).isAlmostZero()) {
                    searchView.visibility = View.VISIBLE
                }
            }
        }
        a.duration = 120
        cardView.startAnimation(a)
    }

    fun switchToEmptyState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = isSearchBarVisible()
        val marginUpdateNeeded = currCardMargin > 0 || !currCardRadius.isAlmostZero()
        if (!marginUpdateNeeded && !searchBarUpdateNeeded)
        {
            return
        }

        setSearchBarState(false)

        val a = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (marginUpdateNeeded) {
                    val margin = (cardMargin!! - cardMargin!! * interpolatedTime).toInt()
                    val radius = cardRadius!! - cardRadius!! * interpolatedTime
                    cardView.radius = radius
                    cardLayoutParams.setMargins(margin, margin, margin, margin)
                    cardView.layoutParams = cardLayoutParams
                }

                if (searchBarUpdateNeeded) {
                    searchView.alpha = 1 - interpolatedTime
                }

                if (1f - interpolatedTime < 0.01) {
                    searchView.visibility = View.GONE
                }
            }
        }
        a.duration = 120
        cardView.startAnimation(a)

    }

    private fun getColor(id: Int) =
        ContextCompat.getColor(context!!, id)

    private fun setSearchBarState(isDefault: Boolean = true) {
        if (isDefault) {
            toolbar?.navigationIcon
                ?.setColorFilter(getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP)
            cardView.setCardBackgroundColor(getColor(android.R.color.white))
            cardView.cardElevation = cardElevation!!
        } else {
            toolbar?.navigationIcon
                ?.setColorFilter(ContextCompat.getColor(context!!, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
            cardView.setCardBackgroundColor(getColor(R.color.colorPrimary))
            cardView.cardElevation = 0f
        }
    }

    private fun isSearchBarVisible() = searchView.visibility == View.VISIBLE

    private fun Float.isAlmostZero() = this < 0.01
}