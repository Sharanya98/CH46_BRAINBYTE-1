package com.brainbyte.healthareana.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.HealthArenaApplication
import com.brainbyte.healthareana.R

class FragmentSplash : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        val userManager = (requireActivity().application as HealthArenaApplication).appComponent.userManager()

        if(!userManager.isUserLoggedIn()) {

        } else {

        }
    }
}