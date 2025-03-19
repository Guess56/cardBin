package com.example.searchbin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.domain.model.BankInfo

class EnteringAdapter() : RecyclerView.Adapter<EnteringViewHolder>() {

    var onItemClickListener: EnteringViewHolder.OnItemClickListener? = null

    private var items: List<BankInfo> = emptyList()

    fun updateItems(newItems: List<BankInfo>) {
        val oldItems = items
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].bin == newItems[newItemPosition].bin
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        })
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnteringViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return EnteringViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EnteringViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener = onItemClickListener)
    }
}

