package com.jianastrero.yelpr.extension

val Number.twoDecimal: Float
    get() {
        val x = (this.toFloat() * 100).toInt()
        return x / 100f
    }

val Number.meters: String
    get() {
        val x = twoDecimal

        return if (x > 1000f) "${(x / 1000).twoDecimal} km" else "$x meters"
    }