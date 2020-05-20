package com.jianastrero.yelpr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.databinding.ItemBusinessBinding
import com.jianastrero.yelpr.extension.into
import com.jianastrero.yelpr.model.Business

class BusinessAdapter : ListAdapter<Business, BusinessAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Business>() {
        override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_business,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[holder.adapterPosition]

        holder.binding.item = item
        holder.binding.isGrid = false

        item.imageUrl.into(holder.binding.imageView)
    }

    class ViewHolder(val binding: ItemBusinessBinding) : RecyclerView.ViewHolder(binding.root)
}