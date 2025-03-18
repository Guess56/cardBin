package com.example.searchbin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.domain.model.CardInfo

class EnteringAdapter() : RecyclerView.Adapter<EnteringViewHolder> () {

    var onItemClickListener: EnteringViewHolder.OnItemClickListener? = null

    private var items: List<CardInfo> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnteringViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entering_bin_fragment, parent, false)
        return EnteringViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EnteringViewHolder, position: Int) {
        holder.bind(items[position],onItemClickListener = onItemClickListener)
    }

}
