package com.jianastrero.yelpr.binding

import androidx.databinding.ObservableField

class NonNullObservableField<T>(value: T) : ObservableField<T>(value) {

    override fun get(): T {
        return super.get()!!
    }

    override fun set(value: T) {
        super.set(value!!)
    }
}