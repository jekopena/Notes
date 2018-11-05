package com.jekoding.notes.framework

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jekoding.notes.BR

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T, listener: (T) -> Unit) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        itemView.setOnClickListener { listener(item) }
    }
}