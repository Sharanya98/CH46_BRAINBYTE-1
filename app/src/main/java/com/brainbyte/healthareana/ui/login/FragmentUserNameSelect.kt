package com.brainbyte.healthareana.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.databinding.FragmentUserNameBinding

class FragmentUserNameSelect : Fragment() {
    private lateinit var binding: FragmentUserNameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            findNavController().navigate(FragmentUserNameSelectDirections.actionFragmentUserNameSelectToFragmentGender())
        }
    }
}