package com.jianastrero.yelpr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.ItemImageBinding
import com.jianastrero.yelpr.extension.into
import com.jianastrero.yelpr.extension.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SquareImageAdapter : ListAdapter<String, SquareImageAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
) {

    private var onItemClickListener: (Int) -> Unit = { }

    var isListView = true
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Main).launch {
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[holder.adapterPosition]

        holder.binding.ivImage.setOnClickListener {
            "onClick: ${holder.adapterPosition}".log()
            onItemClickListener(holder.adapterPosition)
        }

        item.into(holder.binding.ivImage)
    }

    fun setOnItemClickListener(onItemClickListener: (Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}