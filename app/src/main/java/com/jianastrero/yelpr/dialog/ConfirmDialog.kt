package com.jianastrero.yelpr.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.DialogConfirmBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun askConfirmation(
    fragmentManager: FragmentManager,
    block: ConfirmDialog.Builder.() -> Unit
) = withContext(Dispatchers.Default) {

    var ok: Boolean? = null

    val dialog = withContext(Dispatchers.Main) {
        ConfirmDialog.Builder()
            .apply(block)
            .build()
            .apply {
                addOnPositiveClickListener {
                    ok = true
                }
                addOnNegativeClickListener {
                    ok = false
                }
            }
    }

    dialog.show(fragmentManager, null)

    while (ok == null) {
        delay(250)
    }

    ok ?: false
}

class ConfirmDialog : DialogFragment() {

    private lateinit var binding: DialogConfirmBinding

    private var onNegativeClickListeners: MutableList<() -> Unit> = mutableListOf()
    private var onPositiveClickListeners: MutableList<() -> Unit> = mutableListOf()

    var title: String = ""
        get() = if (!::binding.isInitialized) {
            field
        } else {
            binding.title ?: field
        }
        set(value) {
            field = value

            if (::binding.isInitialized) {
                binding.title = value
            }
        }

    var message: String = ""
        get() = if (!::binding.isInitialized) {
            field
        } else {
            binding.message ?: field
        }
        set(value) {
            field = value

            if (::binding.isInitialized) {
                binding.message = value
            }
        }

    var negativeText: String = ""
        get() = if (!::binding.isInitialized) {
            field
        } else {
            binding.negativeText ?: field
        }
        set(value) {
            field = value

            if (::binding.isInitialized) {
                binding.negativeText = value
            }
        }

    var positiveText: String = ""
        get() = if (!::binding.isInitialized) {
            field
        } else {
            binding.positiveText ?: field
        }
        set(value) {
            field = value

            if (::binding.isInitialized) {
                binding.positiveText = value
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_confirm,
            container,
            false
        )

        binding.title = title
        binding.message = message
        binding.negativeText = negativeText
        binding.positiveText = positiveText

        binding.setOnPositiveClickListener {
            onPositiveClickListeners.forEach { it.invoke() }
            dismiss()
        }

        binding.setOnNegativeClickListener {
            onNegativeClickListeners.forEach { it.invoke() }
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
        }
    }

    fun addOnNegativeClickListener(onNegativeClickListener: () -> Unit) {
        onNegativeClickListeners.add(onNegativeClickListener)
    }

    fun addOnPositiveClickListener(onPositiveClickListener: () -> Unit) {
        onPositiveClickListeners.add(onPositiveClickListener)
    }

    class Builder {

        private val dialog = ConfirmDialog()
        private var onNegativeClickListener: () -> Unit = { }
        private var onPositiveClickListener: () -> Unit = { }

        fun setTitle(title: String): Builder = this.apply {
            dialog.title = title
        }

        fun setMessage(message: String): Builder = this.apply {
            dialog.message = message
        }

        fun setNegativeText(negativeText: String): Builder = this.apply {
            dialog.negativeText = negativeText
        }

        fun setPositiveText(positiveText: String): Builder = this.apply {
            dialog.positiveText = positiveText
        }

        fun setOnNegativeClickListener(onNegativeClickListener: () -> Unit): Builder = this.apply {
            this.onNegativeClickListener = onNegativeClickListener
        }

        fun setOnPositiveClickListener(onPositiveClickListener: () -> Unit): Builder = this.apply {
            this.onPositiveClickListener = onPositiveClickListener
        }

        fun build() = dialog.apply {
            addOnNegativeClickListener(onNegativeClickListener)
            addOnPositiveClickListener(onPositiveClickListener)
        }
    }
}