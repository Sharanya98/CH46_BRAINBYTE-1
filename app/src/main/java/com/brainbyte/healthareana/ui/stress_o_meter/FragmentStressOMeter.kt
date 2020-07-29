package com.brainbyte.healthareana.ui.stress_o_meter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.databinding.FragmentStressOMeterDetailBinding
import com.brainbyte.healthareana.databinding.FragmentStressOMeterStartBinding

class FragmentStressOMeter : Fragment() {

    private lateinit var binding: FragmentStressOMeterStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            stressContinueButton.setOnClickListener {
                findNavController().navigate(FragmentStressOMeterDirections.actionFragmentStressOMeterToFragmentStressOMeterDetail())
            }
        }

    }
}