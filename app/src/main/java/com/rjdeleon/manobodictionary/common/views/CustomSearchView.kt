package com.rjdeleon.manobodictionary.common.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.rjdeleon.manobodictionary.R
import kotlinx.android.synthetic.main.custom_search_view.view.*

class CustomSearchView(context: Context, attrs: AttributeSet)
    : RelativeLayout(context, attrs) {

    var toolbar: Toolbar?
    var searchFocusChangeListener: (() -> Unit)? = null
    var searchTextChangeListener: ((String) -> Unit)? = null

    private var mCardLayoutParams: LayoutParams
    private var mCardMargin: Int? = null
    private var mCardRadius: Float? = null
    private var mCardElevation: Float? = null

    init {
        View.inflate(context, R.layout.custom_search_view, this)
        toolbar = cardToolbar

        /* Retrieve view attributes then apply */
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView)
        searchView.queryHint = attributes.getString(R.styleable.CustomSearchView_searchHint)
        cardView.radius = attributes.getDimension(R.styleable.CustomSearchView_cardRadius, 0f)
        attributes.recycle()

        /* Retrieve card margins and radius */
        mCardLayoutParams = cardView.layoutParams as LayoutParams
        mCardMargin = mCardLayoutParams.topMargin
        mCardElevation = cardView.cardElevation
        mCardRadius = cardView.radius
        setupSearchView()
    }

    private fun setupSearchView() {

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
                searchTextChangeListener?.invoke(query ?: "")
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                searchTextChangeListener?.invoke(query ?: "")
                return true
            }
        })
    }

    // region Toolbar animations

    fun switchToDefaultState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = !searchView.isVisible()
        val marginUpdateNeeded = currCardMargin == 0 && currCardRadius.isAlmostZero()
        val searchBarUpdateActionImmediate: () -> Unit = {
            setSearchBarState(true)
            viewLayout.setBackgroundColor(Color.TRANSPARENT)
            cardView.radius = mCardRadius!!
        }

        val searchBarUpdateActionIncremental: (Float) -> Unit = { interpolatedTime ->
            searchView.alpha = interpolatedTime
        }

        val marginUpdateAction: (Float) -> Unit = { interpolatedTime ->
            val margin = (mCardMargin!! * interpolatedTime).toInt()
            mCardLayoutParams.setMargins(margin, margin, margin, margin)
            cardView.layoutParams = mCardLayoutParams
        }

        val animationFinishedAction: () -> Unit = {
            searchView.visibility = View.VISIBLE
        }

        performToolbarAnimations(cardView,
            marginUpdateNeeded,
            marginUpdateAction,
            searchBarUpdateNeeded,
            searchBarUpdateActionImmediate,
            searchBarUpdateActionIncremental,
            animationFinishedAction)
    }

    fun switchToSearchState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = !searchView.isVisible()
        val marginUpdateNeeded = currCardMargin > 0 && !currCardRadius.isAlmostZero()
        val searchBarUpdateActionImmediate: () -> Unit = {
            setSearchBarState(true)
        }

        val searchBarUpdateActionIncremental: (Float) -> Unit = { interpolatedTime ->
            searchView.alpha = interpolatedTime
        }

        val marginUpdateAction: (Float) -> Unit = { interpolatedTime ->
            val margin = (currCardMargin - currCardMargin * interpolatedTime).toInt()
            mCardLayoutParams.setMargins(margin, margin, margin, margin)
            cardView.layoutParams = mCardLayoutParams
        }

        val animationFinishedAction: () -> Unit = {
            searchView.visibility = View.VISIBLE
            cardView.radius = 0f
            viewLayout.setBackgroundColor(Color.WHITE)
        }

        performToolbarAnimations(cardView,
            marginUpdateNeeded,
            marginUpdateAction,
            searchBarUpdateNeeded,
            searchBarUpdateActionImmediate,
            searchBarUpdateActionIncremental,
            animationFinishedAction)
    }

    fun switchToEmptyState() {
        val currCardMargin = getCardMargin()
        val currCardRadius = getCardRadius()
        val searchBarUpdateNeeded = searchView.isVisible()
        val marginUpdateNeeded = currCardMargin > 0 || !currCardRadius.isAlmostZero()

        val searchBarUpdateActionImmediate: () -> Unit = {
            setSearchBarState(false)
            viewLayout.setBackgroundColor(Color.TRANSPARENT)
            cardView.background.alpha = 0
        }

        val searchBarUpdateActionIncremental: (Float) -> Unit = { interpolatedTime ->
            searchView.alpha = 1 - interpolatedTime
        }

        val marginUpdateAction: (Float) -> Unit = { interpolatedTime ->
            val margin = (mCardMargin!! - mCardMargin!! * interpolatedTime).toInt()
            mCardLayoutParams.setMargins(margin, margin, margin, margin)
            cardView.layoutParams = mCardLayoutParams
        }

        val animationFinishedAction: () -> Unit = {
            searchView.visibility = View.GONE
            cardView.radius = 0f
        }

        performToolbarAnimations(cardView,
            marginUpdateNeeded,
            marginUpdateAction,
            searchBarUpdateNeeded,
            searchBarUpdateActionImmediate,
            searchBarUpdateActionIncremental,
            animationFinishedAction)
    }

    private fun setSearchBarState(isDefault: Boolean = true) {
        if (isDefault) {
            toolbar?.navigationIcon
                ?.setColorFilter(getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP)
            cardView.setCardBackgroundColor(Color.WHITE)
            cardView.cardElevation = mCardElevation!!
        } else {
            toolbar?.navigationIcon
                ?.setColorFilter(ContextCompat.getColor(context!!, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
            cardView.setCardBackgroundColor(Color.TRANSPARENT)
            cardView.cardElevation = 0f
        }
    }

    private fun performToolbarAnimations(viewToAnimate: View,
                                         marginUpdateNeeded: Boolean,
                                         marginUpdateAction: (interpolatedTime: Float) -> Unit,
                                         searchBarUpdateNeeded: Boolean,
                                         searchBarUpdateActionImmediate: () -> Unit,
                                         searchBarUpdateActionIncremental: (interpolatedTime: Float) -> Unit,
                                         animationFinishedAction: () -> Unit) {

        if (!marginUpdateNeeded && !searchBarUpdateNeeded)
            return

        searchBarUpdateActionImmediate.invoke()

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (marginUpdateNeeded) {
                    marginUpdateAction.invoke(interpolatedTime)
                }

                if (searchBarUpdateNeeded) {
                    searchBarUpdateActionIncremental.invoke(interpolatedTime)
                }
            }
        }
        a.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                animationFinishedAction.invoke()
            }
        })
        a.duration = 500
        viewToAnimate.startAnimation(a)
    }

    // endregion

    // region Extensions and utilities

    private fun getCardMargin() = (cardView.layoutParams as LayoutParams).topMargin

    private fun getCardRadius() = cardView.radius

    private fun getColor(id: Int) = ContextCompat.getColor(context!!, id)

    private fun View.isVisible() = this.visibility == View.VISIBLE

    private fun Float.isAlmostZero() = this < 0.01

    // endregion
}