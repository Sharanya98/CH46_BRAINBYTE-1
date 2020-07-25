package com.brainbyte.healthareana.ui.step_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.databinding.FragmentStepUpBinding

class FragmentStepUp : Fragment() {

    private lateinit var binding: FragmentStepUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepUpBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imReadyBtn.setOnClickListener {
            findNavController().navigate(FragmentStepUpDirections.actionFragmentStepUpToFragmentStepUpDetails())
        }
    }
}