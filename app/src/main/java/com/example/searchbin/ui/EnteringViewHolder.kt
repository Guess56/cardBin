package com.example.searchbin.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.domain.model.CardInfo


class EnteringViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val url: TextView = itemView.findViewById(R.id.url)
    private val phone: TextView = itemView.findViewById(R.id.phone)
    private val coord: TextView = itemView.findViewById(R.id.coord)

    fun bind(item: CardInfo, onItemClickListener : OnItemClickListener?) {

        url.text = item.bank.url
        phone.text = item.bank.phone
        coord.text = item.bank.city

        itemView.setOnClickListener{
            onItemClickListener?.onItemClick(item)
        }
    }
    fun interface OnItemClickListener {
        fun onItemClick(item: CardInfo)
    }
}
