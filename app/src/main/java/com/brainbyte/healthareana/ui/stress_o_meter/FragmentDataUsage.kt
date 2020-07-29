package com.brainbyte.healthareana.ui.stress_o_meter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentStressOMeterMostUsedBinding

class FragmentDataUsage : Fragment() {

    private lateinit var binding: FragmentStressOMeterMostUsedBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterMostUsedBinding.inflate(layoutInflater,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            youtubeAppNameTime.text = "1 GB"

            mostUsedAppNameTime.text = "3 GB"

            twitterAppNameTime.text = "101 MB"

            facebookAppNameTime.text = "5 GB"

        }
    }
}