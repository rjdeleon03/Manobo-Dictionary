package com.rjdeleon.manobodictionary.base

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder> (context: Context)
    : RecyclerView.Adapter<VH>() {

    protected val mInflater = LayoutInflater.from(context)
}