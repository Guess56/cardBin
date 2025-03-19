package com.example.searchbin.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchbin.databinding.EnteringBinFragmentBinding
import com.example.searchbin.domain.model.BankInfo
import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.utils.HistoryScreenState
import com.example.searchbin.utils.ScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnteringFragment : Fragment() {

    private var _binding: EnteringBinFragmentBinding? = null

    val binding: EnteringBinFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<EnteringViewModel>()
    private val enteringAdapter = EnteringAdapter()
    private var bin: String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EnteringBinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextWatcher()
        setupObservers()
        binding.rvSearchHistory.adapter = enteringAdapter
        binding.rvSearchHistory.layoutManager = LinearLayoutManager(requireContext())
        viewModel.loadHistory()
        binding.url.setOnClickListener {
            openLink()
        }
        binding.phone.setOnClickListener {
            openPhone()
        }
        binding.coord.setOnClickListener {
            openMap()
        }
    }

    private fun setupTextWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (p0 != null && p0.length >= 6) {
                    viewModel.searchDebounce(p0.toString())
                    bin = p0.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.bin.addTextChangedListener(simpleTextWatcher)
    }

    private fun setupObservers() {
        viewModel.getHistoryState().observe(viewLifecycleOwner){ state ->
            when (state) {
                is HistoryScreenState.Empty -> {
                    Log.d("Card", "Пустой список данных")
                }
                is HistoryScreenState.Content -> {
                    enteringAdapter.updateItems(state.data)
                    Log.e("123","History${state.data}")
                }
            }
        }
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {}
                is ScreenState.Empty -> {
                    Log.d("Card", "Пустой список данных")
                }

                is ScreenState.Error -> {
                    Log.e("Card", "Ошибка: ${state.message}")
                }

                is ScreenState.Content -> {
                    if (state.data.isNotEmpty()) {
                        Log.e("Card", "успех: ${state.data}")
                        updateTextView(state.data)
                    } else {
                        Log.d("Card", "Пустой список данных")
                    }
                    viewModel.viewModelScope.launch {
                        viewModel.addCardDb(bin, state.data.first().bank)
                    }
                }
            }
        }
    }

    private fun updateTextView(data: List<CardInfo>) {
        for (i in data) {
            binding.url.text = i.bank.url
            binding.phone.text = i.bank.phone
            binding.coord.text = i.bank.city
        }
    }

    private fun openLink() {
        if (binding.url.text.equals("url не указан")) {
            Toast.makeText(context, "Api передал пустой url", Toast.LENGTH_SHORT).show()
        } else {
            val urlText = binding.url.text.toString()
            if (urlText.startsWith("http")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlText))
                requireContext().startActivity(intent)
            }
        }
    }

    private fun openPhone() {
        if (binding.phone.text.equals("Номер не указан")) {
            Toast.makeText(context, "Api передал пустой номер", Toast.LENGTH_SHORT).show()
        } else {
            val phoneText = binding.phone.text.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneText"))
            requireContext().startActivity(intent)
        }
    }

    private fun openMap() {
        if (binding.coord.text.equals("Город не указан")) {
            Toast.makeText(context, "Api передал пустой город", Toast.LENGTH_SHORT).show()
        } else {
            val cityText = binding.coord.text.toString()
            val uri = Uri.parse("geo:0,0?q=$cityText")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireContext().startActivity(intent)
        }
    }
}
