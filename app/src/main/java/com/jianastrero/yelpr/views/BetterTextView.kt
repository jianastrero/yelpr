package com.jianastrero.yelpr.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.getFontOrThrow
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.extensions.dp
import com.jianastrero.yelpr.extensions.sp
import java.lang.Exception

class BetterTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    val widthF: Float
        get() = width.toFloat()

    val heightF: Float
        get() = height.toFloat()

    var text: String = ""
        set(value) {
            field = value
            invalidate()
        }

    var textSize: Float = 16.sp
        set(value) {
            field = value
            fillPaint.textSize = field
            strokePaint.textSize = field
            invalidate()
        }

    var textColor: Int = 0xff000000.toInt()
        set(value) {
            field = value
            fillPaint.color = field
            invalidate()
        }

    var strokeColor: Int = 0xffffffff.toInt()
        set(value) {
            field = value
            strokePaint.color = field
            invalidate()
        }

    var strokeWidth: Float = 1.dp
        set(value) {
            field = value
            strokePaint.strokeWidth = field
            invalidate()
        }

    var fontFamily: Typeface? = Typeface.DEFAULT
        set(value) {
            field = value
            fillPaint.typeface = field ?: Typeface.DEFAULT
            strokePaint.typeface = field ?: Typeface.DEFAULT
            invalidate()
        }

    var maxLines: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    var ellipsize: Ellipsize = Ellipsize.NONE
        set(value) {
            field = value
            invalidate()
        }

    private val fillPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = textColor
        textSize = this@BetterTextView.textSize
        typeface = fontFamily
    }

    private val strokePaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        color = strokeColor
        textSize = this@BetterTextView.textSize
        typeface = fontFamily
        strokeWidth = this@BetterTextView.strokeWidth
        strokeMiter = 0f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val bounds = Rect()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BetterTextView,
            0,
            0
        ).apply {
            try {

                val jianStrokeColor =
                    getColor(R.styleable.BetterTextView_jianStrokeColor, -1)
                if (jianStrokeColor != -1) {
                    strokeColor = jianStrokeColor
                }

                text = getString(R.styleable.BetterTextView_jianText) ?: ""

                val jianTextColor =
                    getColor(R.styleable.BetterTextView_jianTextColor, -1)
                if (jianTextColor != -1) {
                    textColor = jianTextColor
                }

                val jianTextSize =
                    getDimensionPixelSize(R.styleable.BetterTextView_jianTextSize, -1)
                if (jianTextSize != -1) {
                    textSize = jianTextSize.toFloat()
                }

                val jianMaxLines =
                    getInt(R.styleable.BetterTextView_jianMaxLines, -1)
                if (jianMaxLines != -1) {
                    maxLines = jianMaxLines
                }

                val jianEllipsize =
                    getInt(R.styleable.BetterTextView_jianEllipsize, -1)
                if (jianEllipsize != -1) {
                    ellipsize = Ellipsize.get(jianEllipsize)
                }

                val jianFontFamily =
                    getFontOrThrow(R.styleable.BetterTextView_jianFontFamily)
                fontFamily = jianFontFamily
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        strokePaint.getTextBounds(text, 0, text.length, bounds)
        setMeasuredDimension(
            (bounds.width() + strokeWidth * 2).toInt(),
            (bounds.height() + strokeWidth * 2).toInt()
        )
    }

    override fun onDraw(_canvas: Canvas?) {
        super.onDraw(_canvas)

        _canvas?.let { canvas ->
            canvas.drawColor(Color.TRANSPARENT)

            canvas.drawText(text, -strokeWidth, heightF - strokeWidth, strokePaint)
            canvas.drawText(text, -strokeWidth, heightF - strokeWidth, fillPaint)
        }
    }

    enum class Ellipsize(val id: Int) {
        START(0), MIDDLE(1), END(2), NONE(3);

        companion object {

            fun get(id: Int) =
                when (id) {
                    START.id -> START
                    MIDDLE.id -> MIDDLE
                    END.id -> END
                    else -> NONE
                }
        }
    }
}

@BindingAdapter("jianastrero:text")
fun BetterTextView.bindingText(text: String) {
    this.text = text
}

@InverseBindingAdapter(attribute = "jianastrero:text")
fun BetterTextView.inverseBindingText(): String {
    return text
}