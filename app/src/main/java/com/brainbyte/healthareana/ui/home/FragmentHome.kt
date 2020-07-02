package com.brainbyte.healthareana.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentBaseBinding

class FragmentHome : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBaseBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}