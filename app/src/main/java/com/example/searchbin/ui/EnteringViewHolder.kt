package com.example.searchbin.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.databinding.EnteringBinFragmentBinding
import com.example.searchbin.domain.model.CardInfo

class EnteringViewHolder(
    private val binding: EnteringBinFragmentBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CardInfo, onItemClickListener: OnItemClickListener?) {
        binding.url.text = item.bank.url
        binding.phone.text = item.bank.phone
        binding.coord.text = item.bank.city

        binding.url.setOnClickListener {
            if (binding.url.text.equals("url не указан")) {
                Toast.makeText(context, "Api передал пустой url", Toast.LENGTH_SHORT).show()
            } else {
                val urlText = binding.url.text.toString()
                if (urlText.startsWith("http")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlText))
                    context.startActivity(intent)
                }
            }
        }
        binding.phone.setOnClickListener {
            if (binding.url.text.equals("Номер не указан")) {
                Toast.makeText(context, "Api передал пустой номер", Toast.LENGTH_SHORT).show()
            } else {
                val phoneText = binding.phone.text.toString()
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneText"))
                binding.root.context.startActivity(intent)
            }
        }
        binding.coord.setOnClickListener {
            if (binding.url.text.equals("Город не указан")) {
                Toast.makeText(context, "Api передал пустой город", Toast.LENGTH_SHORT).show()
            } else {
                val cityText = binding.coord.text.toString()
                val uri = Uri.parse("geo:0,0?q=$cityText")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                binding.root.context.startActivity(intent)
            }
        }

        binding.root.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: CardInfo)
    }
}