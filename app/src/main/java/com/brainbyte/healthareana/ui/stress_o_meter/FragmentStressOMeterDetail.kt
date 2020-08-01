package com.brainbyte.healthareana.ui.stress_o_meter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.databinding.FragmentStressOMeterDetailBinding

class FragmentStressOMeterDetail : Fragment() {

    private lateinit var binding: FragmentStressOMeterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            downTimeCard.setOnClickListener {
                findNavController().navigate(FragmentStressOMeterDetailDirections.actionFragmentStressOMeterDetailToFragmentDownTime())
            }

            appLimitCard.setOnClickListener {
                findNavController().navigate(FragmentStressOMeterDetailDirections.actionFragmentStressOMeterDetailToFragmentStressOMeterAppLimit())
            }

            mostUsedCard.setOnClickListener {
                findNavController().navigate(FragmentStressOMeterDetailDirections.actionFragmentStressOMeterDetailToFragmentStressOMeterMostUsed())
            }

            dataUsageCard.setOnClickListener {
                findNavController().navigate(FragmentStressOMeterDetailDirections.actionFragmentStressOMeterDetailToFragmentStressOMeterDataUsage())
            }
        }

    }
}