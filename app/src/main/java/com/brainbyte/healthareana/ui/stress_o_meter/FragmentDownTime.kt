package com.brainbyte.healthareana.ui.stress_o_meter

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.databinding.FragmentStressOMeterDowntimeBinding
import com.brainbyte.healthareana.databinding.StressCongratsScreenBinding

class FragmentDownTime : Fragment() {

    private lateinit var binding: FragmentStressOMeterDowntimeBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStressOMeterDowntimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.downtimeReadyBtn.setOnClickListener {
            showAchievementDialog()
        }
    }

    private fun showAchievementDialog() {
        val stressCongratsDialog = AlertDialog.Builder(requireContext())
        val stressCongratsScreenBinding = StressCongratsScreenBinding.inflate(layoutInflater)

        stressCongratsDialog.setView(stressCongratsScreenBinding.root)
        val congratsDialog = stressCongratsDialog.create()
        congratsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        congratsDialog.show()
        stressCongratsScreenBinding.coinShowerView.playAnimation()
        stressCongratsScreenBinding.stepUpContinue.setOnClickListener {
            congratsDialog.dismiss()
        }

    }
}