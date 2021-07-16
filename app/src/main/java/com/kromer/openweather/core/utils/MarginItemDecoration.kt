package com.kromer.openweather.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val leftSpaceSize: Int,
    private val topSpaceSize: Int,
    private val rightSpaceSize: Int,
    private val bottomSpaceSize: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topSpaceSize
            }
            left = leftSpaceSize
            right = rightSpaceSize
            bottom = bottomSpaceSize
        }
    }
}