package com.example.searchbin.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchbin.databinding.EnteringBinFragmentBinding
import com.example.searchbin.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnteringFragment: Fragment() {

    private var _binding: EnteringBinFragmentBinding? = null

    val binding: EnteringBinFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<EnteringViewModel>()

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

    }

    private fun setupTextWatcher() {
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // в данный момент не используется
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                if (p0 != null && p0.length >= 6){
                viewModel.searchDebounce(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        binding.bin.addTextChangedListener(simpleTextWatcher)
    }
    private fun setupObservers() {
        viewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {}
                is ScreenState.Empty -> {}
                is ScreenState.Error -> {}
                is ScreenState.Content -> {
                    Log.d("Card","${state.data}")
                }
            }
        }
    }
}