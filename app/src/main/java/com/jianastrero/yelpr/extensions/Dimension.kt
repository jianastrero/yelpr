package com.jianastrero.yelpr.extensions

import android.content.res.Resources
import android.util.TypedValue

val Number.dp: Float
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

val Number.px: Float
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

val Number.sp: Float
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )