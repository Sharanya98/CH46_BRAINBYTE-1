package com.brainbyte.healthareana.ui.step_up

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentStepUpDetailBinding
import com.brainbyte.healthareana.databinding.StepUpCongratsScreenBinding
import com.brainbyte.healthareana.databinding.TrophyScratchLayoutBinding
import com.cooltechworks.views.ScratchImageView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*

class FragmentStepUpDetails : Fragment() {

    private lateinit var binding: FragmentStepUpDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepUpDetailBinding.inflate(layoutInflater, container, false)

        binding.stepUpProgressBar.setProgressWithAnimation(50F, 5000)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpProgressChart()
        addDataSetToChart()
       showAchievementDialog()
    }



    private fun setUpProgressChart() {

        binding.stepProgressChart.apply {

            description.isEnabled = false

            setMaxVisibleValueCount(7)

            setPinchZoom(false)

            setDrawGridBackground(false)

            val xAxis = this.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.valueFormatter = DayAxisFormatter()

            axisLeft.setDrawGridLines(false)

            animateXY(1500, 2500)

            setDrawValueAboveBar(true)

            renderer = StepUpBarChartRenderer(this, this.animator, this.viewPortHandler)
        }

    }

    private fun addDataSetToChart() {
        val values = ArrayList<BarEntry>()

        for (i in 1 until 8) {
            val multi: Float = (8 + 1).toFloat()
            val value = (Math.random() * multi).toFloat() + multi / 3
            values.add(BarEntry(i.toFloat(), value, ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)))
        }

        val barDataSet = BarDataSet(values, "Steps Count")
        barDataSet.color = ContextCompat.getColor(requireContext(), R.color.color_step_up)
        barDataSet.setDrawValues(true)
        barDataSet.setDrawIcons(true)

        val barData = BarData(barDataSet)
        binding.stepProgressChart.apply {
            data = barData
            setFitBars(true)
        }

    }


    private fun showAchievementDialog() {

        val stepCongratsDialog = AlertDialog.Builder(requireContext())
        val stepUpCongratsScreenBinding = StepUpCongratsScreenBinding.inflate(layoutInflater)

        stepCongratsDialog.setView(stepUpCongratsScreenBinding.root)
        val congratsDialog = stepCongratsDialog.create()
        congratsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        congratsDialog.show()
        stepUpCongratsScreenBinding.coinShowerView.playAnimation()
        stepUpCongratsScreenBinding.stepUpContinue.setOnClickListener {
            congratsDialog.dismiss()
            showScratchDialogBox()

        }

    }

    private fun showScratchDialogBox() {
        val scratchDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        val trophyScratchLayoutBinding = TrophyScratchLayoutBinding.inflate(layoutInflater)

        trophyScratchLayoutBinding.apply {

            scratchView.setRevealListener(object : ScratchImageView.IRevealListener {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onRevealed(iv: ScratchImageView?) {
                }

                override fun onRevealPercentChangedListener(siv: ScratchImageView?, percent: Float) {
                    if(percent > 75.0) siv?.reveal()
                }

            })
        }

        scratchDialogBuilder.setView(trophyScratchLayoutBinding.root)
        val scratchDialog = scratchDialogBuilder.create()
        scratchDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        scratchDialog.show()

    }

}