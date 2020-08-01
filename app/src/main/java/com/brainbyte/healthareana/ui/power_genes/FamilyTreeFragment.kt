package com.brainbyte.healthareana.ui.power_genes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentFamilyTreeBinding

class FamilyTreeFragment : Fragment() {
    private lateinit var binding: FragmentFamilyTreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyTreeBinding.inflate(inflater, container, false)
        return binding.root
    }
}