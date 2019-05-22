package com.rjdeleon.manobodictionary.common

import androidx.lifecycle.LifecycleOwner
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

fun <T> LiveData<T>.observeAllButSeparateFirstUpdate(
    owner: LifecycleOwner, observer: Observer<T>, onFirstUpdate: (T) -> Unit) {

    var isFirstUpdate = true
    observe(owner, Observer {
        if (isFirstUpdate) {
            isFirstUpdate = false
            onFirstUpdate.invoke(it)
            return@Observer
        }
        observer.onChanged(it)
    })
}