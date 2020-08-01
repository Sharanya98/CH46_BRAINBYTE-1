package com.brainbyte.healthareana.ui.sobriety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentAddictionClockBinding

class FragmentAddictionClock : Fragment() {
    private lateinit var binding: FragmentAddictionClockBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddictionClockBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            activity?.finish()
        }
        return binding.root
    }
}