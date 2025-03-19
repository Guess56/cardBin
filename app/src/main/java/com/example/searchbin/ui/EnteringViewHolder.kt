package com.example.searchbin.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.domain.model.BankInfo

class EnteringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvBin: TextView = itemView.findViewById(R.id.Bin)

    fun bind(item: BankInfo, onItemClickListener: OnItemClickListener?) {
        tvBin.text = item.bin

        itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: BankInfo)
    }
}