package com.jianastrero.yelpr.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.extension.dp

class BackdropForeground @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPath: Path = Path()

    private val widthF: Float
        get() = width.toFloat()

    private val heightF: Float
        get() = height.toFloat()

    private val colorSecondary = ContextCompat.getColor(context, R.color.colorSecondary)

    private val dp32 = 32.dp

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            mPath.reset()
            mPath.addRoundRect(
                0f,
                0f,
                widthF,
                heightF,
                floatArrayOf(dp32, dp32, dp32, dp32, 0f, 0f, 0f, 0f),
                Path.Direction.CW
            )
            mPath.fillType = Path.FillType.INVERSE_EVEN_ODD
            it.clipPath(mPath)
            it.drawColor(colorSecondary)
        }
    }
}