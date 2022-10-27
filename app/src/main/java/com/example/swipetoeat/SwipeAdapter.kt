package com.example.swipetoeat

import android.content.Context
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

class SwipeAdapter(private val context: Context, private val list: List<Int>) : BaseAdapter() {
    override fun getCount(): Int {
        return 20
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (parent != null) {
            return convertView
                ?: LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_koloda, parent, false)
        }
        return null
    }
}

