package com.rjdeleon.manobodictionary.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}