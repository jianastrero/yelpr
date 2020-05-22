package com.jianastrero.yelpr.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class DynamicScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private var onGhostStartListener: (Float) -> Unit = { }
    private var onGhostDragListener: (Float, Float) -> Unit = { _, _ -> }
    private var onGhostReleaseListener: (Int) -> Unit = { }
    private var ghostStartY = 0f

    var scrollable: Boolean = true

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (scrollable) {
            super.onInterceptTouchEvent(ev)
        } else {
            true
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if (scrollable) {
            super.onTouchEvent(ev)
        } else {
            ev?.let { handleOnTouch(it) }
            true
        }
    }

    fun setOnGhostDragListener(onGhostScrollListener: (Float, Float) -> Unit) {
        this.onGhostDragListener = onGhostScrollListener
    }

    fun setOnGhostStartListener(onGhostStartListener: (Float) -> Unit) {
        this.onGhostStartListener = onGhostStartListener
    }

    fun setOnGhostReleaseListener(onGhostReleaseListener: (Int) -> Unit) {
        this.onGhostReleaseListener = onGhostReleaseListener
    }

    private fun ghostDrag(startY: Float, moveY: Float) {
        if (!scrollable) {
            onGhostDragListener(startY, moveY)
        }
    }

    private fun handleOnTouch(ev: MotionEvent) {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                ghostStartY = ev.rawY
                onGhostStartListener(ghostStartY)
            }
            MotionEvent.ACTION_MOVE -> {
                ghostDrag(ghostStartY, ev.rawY)
            }
            MotionEvent.ACTION_UP -> {
                if (!scrollable) {
                    onGhostReleaseListener(
                        if (ghostStartY > ev.rawY) {
                            0 // UP
                        } else {
                            1 // DOWN
                        }
                    )
                }
            }
        }
    }
}