package com.brainbyte.healthareana.ui.sobriety

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentCalendarBinding
import com.brainbyte.healthareana.util.USER_BD
import com.brainbyte.healthareana.util.USER_SP_KEY
import timber.log.Timber

class AdditionDatePicker : Fragment() {
    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        binding.apply {
            root.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorBackground
                )
            )
            calendarTitle.apply {
                setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                text = "Let's begin your addiction free journey from..."
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.birthPicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                    context?.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE)?.edit()
                        ?.putString(
                            USER_BD, "$dayOfMonth/$monthOfYear/$year"
                        )?.apply()
                    Timber.d("$dayOfMonth/$monthOfYear/$year")
                }
            }
            fab.setOnClickListener { findNavController().navigate(R.id.fragmentAddictionClock) }
        }
        return binding.root
    }
}