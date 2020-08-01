package com.brainbyte.healthareana.ui.stress_o_meter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentStressOMeterAppLimitBinding

class FragmentAppLimit : Fragment() {


    private lateinit var binding: FragmentStressOMeterAppLimitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterAppLimitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            addAppFab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_app_limit_add_button))





        }



    }
}