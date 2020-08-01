package com.brainbyte.healthareana.ui.sobriety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentTimeCostBinding

class TimeCostFragment : Fragment() {
    private lateinit var binding: FragmentTimeCostBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimeCostBinding.inflate(inflater, container, false)
        binding.apply {
            fab.setOnClickListener { findNavController().navigate(R.id.additionDatePicker) }
        }
        return binding.root
    }
}