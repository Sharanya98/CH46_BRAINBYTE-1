package com.brainbyte.healthareana.ui.stress_o_meter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentStressOMeterMostUsedBinding

class FragmentMostUsed : Fragment() {

    private lateinit var binding: FragmentStressOMeterMostUsedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterMostUsedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}