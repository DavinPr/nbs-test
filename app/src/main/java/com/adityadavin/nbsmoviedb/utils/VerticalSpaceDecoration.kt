package com.adityadavin.nbsmoviedb.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceDecoration(private val spaceSize: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        with(outRect) {
            parent.adapter?.let {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceSize
                }
                bottom =
                    if (parent.getChildAdapterPosition(view) == it.itemCount - 1) {
                        spaceSize
                    } else {
                        spaceSize / 2
                    }
            }
        }
    }
}