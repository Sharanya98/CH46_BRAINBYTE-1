package com.brainbyte.healthareana.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.databinding.FragmentBaseBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.profileProgressBar.setProgressWithAnimation(85f, 5000)

        binding.apply {
            fragmentHomeContainer.apply {
                bmiGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentBMI())
                }

                soberityGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToAddictionOnBoarding())
                }

                stepUpGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentStepUp())
                }
                powerGeneGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToDiseaseOnBoardingFragment())
                }
            }
        }

    }
}