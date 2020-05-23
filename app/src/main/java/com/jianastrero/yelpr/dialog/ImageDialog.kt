package com.jianastrero.yelpr.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.DialogImageBinding
import com.jianastrero.yelpr.extension.into
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun showImage(
    fragmentManager: FragmentManager,
    imageUrl: String,
    block: ImageDialog.Builder.() -> Unit
) = CoroutineScope(Dispatchers.Main).launch {
    val dialog = withContext(Dispatchers.Main) {
        ImageDialog.Builder()
            .apply {
                setImageUrl(imageUrl)
            }
            .apply(block)
            .build()
    }

    dialog.show(fragmentManager, null)
}

class ImageDialog : DialogFragment() {

    private lateinit var binding: DialogImageBinding

    var imageUrl: String = ""
        set(value) {
            field = value

            if (::binding.isInitialized) {
                field.into(binding.imageView)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_image,
            container,
            false
        )

        imageUrl.into(binding.imageView)

        binding.root.setOnClickListener { dismiss() }
        binding.imageView.setOnClickListener { dismiss() }

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
            it.setCancelable(true)
            it.setCanceledOnTouchOutside(true)
        }
    }

    class Builder {

        private val dialog = ImageDialog()

        fun setImageUrl(imageUrl: String) = this.apply {
            dialog.imageUrl = imageUrl
        }

        fun build() = dialog
    }
}