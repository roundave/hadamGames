package com.davidmartinez.hadamgames

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class OverlapDecoration : ItemDecoration() {

    fun getItemOffsets(
        outRect: Rect,
        view: com.google.firebase.database.core.view.View?,
        parent: RecyclerView?,
        state: RecyclerView.State?
    ) {
        outRect.set(0, vertOverlap, 0, 0)

    }


    companion object {
        const val vertOverlap = -90
    }

}