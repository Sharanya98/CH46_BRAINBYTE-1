package com.brainbyte.healthareana.ui.bmi

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.BmiCongratsScreenBinding
import com.brainbyte.healthareana.databinding.BmiResultViewBinding
import com.brainbyte.healthareana.databinding.FragmentBmiBinding
import com.brainbyte.healthareana.databinding.TrophyScratchLayoutBinding
import com.cooltechworks.views.ScratchImageView
import com.cooltechworks.views.ScratchTextView
import com.github.anastr.speedviewlib.SpeedTextListener
import com.github.anastr.speedviewlib.components.Section
import com.github.anastr.speedviewlib.util.OnSectionChangeListener
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import java.util.*

class FragmentBMI : Fragment() {

    private lateinit var binding: FragmentBmiBinding

    private var height = 152

    private var weight = 60

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBmiBinding.inflate(layoutInflater, container, false)

        binding.apply {
            heightRuler.selectValue(152)
            heightRuler.setValuePickerListener(object: RulerValuePickerListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onValueChange(selectedValue: Int) {
                    val string = resources.getString(R.string.height_unit, selectedValue)
                    heightDisplay.text = Html.fromHtml(string,Html.FROM_HTML_MODE_COMPACT)
                    height = selectedValue
                }

                override fun onIntermediateValueChange(selectedValue: Int) {
                    val string = resources.getString(R.string.height_unit, selectedValue)
                    heightDisplay.text = string
                }

            })

            weightRuler.selectValue(60)
            weightRuler.setValuePickerListener(object: RulerValuePickerListener {
                override fun onValueChange(selectedValue: Int) {
                    val string = resources.getString(R.string.weight_unit, selectedValue)
                    weightDisplay.text = string
                    weight = selectedValue
                }

                override fun onIntermediateValueChange(selectedValue: Int) {
                    val string = resources.getString(R.string.weight_unit, selectedValue)
                    weightDisplay.text = string
                }

            })


            binding.bmiSubmitButton.setOnClickListener {
                val bmiIndex: Float = (weight.toFloat() / height.toFloat() / height.toFloat()) * 10_000
                showResultDialogBox(bmiIndex)
            }
        }

        return binding.root
    }


    private fun showResultDialogBox(index: Float) {
        val resultDialogBuilder = AlertDialog.Builder(requireContext())
        val resultViewBinding = BmiResultViewBinding.inflate(layoutInflater)
        resultViewBinding.bmiSpeedView.apply {
            sections.clear()
            addSections(Section(0f, .25f, Color.LTGRAY, this.dpTOpx(30f), Section.Style.ROUND))
            addSections(Section(.25f, .50f, Color.GREEN, this.dpTOpx(30f)))
            addSections(Section(.50f, .75f, Color.YELLOW, this.dpTOpx(30f)))
            addSections(Section(.75f, 1f, Color.RED, this.dpTOpx(30f)))

            onSectionChangeListener = object : OnSectionChangeListener {
                override fun invoke(previousSection: Section?, newSection: Section?) {
                    if (previousSection != null) {
                        previousSection.width = resultViewBinding.bmiSpeedView.dpTOpx(30f)
                    }
                    if (newSection != null) {
                        newSection.width = resultViewBinding.bmiSpeedView.dpTOpx(35f)
                    }
                }
            }

            speedTextListener = object : SpeedTextListener {
                override fun invoke(speed: Float): CharSequence {
                    return String.format(Locale.getDefault(), "%.1f", speed)
                }

            }
        }

        resultViewBinding.bmiSpeedView.speedTo(index, 4000)
        resultViewBinding.bmiResultTextView.text = String.format("%.1f", index)
        resultDialogBuilder.setView(resultViewBinding.root)
        val resultDialog = resultDialogBuilder.create()
        resultViewBinding.bmiResultContinueButton.setOnClickListener {
            resultDialog.dismiss()
            showCongratsDialogBox(index)
        }
        resultDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        resultDialog.show()

    }


    @SuppressLint("SetTextI18n")
    private fun showCongratsDialogBox(index: Float) {
        val congratsDialogBuilder = AlertDialog.Builder(requireContext())
        val congratsScreenBinding = BmiCongratsScreenBinding.inflate(layoutInflater)
        var trophy = 0
        congratsScreenBinding.apply {
            coinShowerView.setAnimation("coin_shower.json")
            coinShowerView.repeatCount = 4
           if(index < 18.5) {
               congratsLogo.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_oops_emoji))
               congratsTitle.text = "Oops!!"
               pointsTextView.text = "50 XP"
               congratsQuote.text = "You need to Work on Yourself to earn a Fit Badge"
               congratsSuggestion.text =  "Your BMI Follows under Underweight, You should be more cautious."
            } else if(index >= 18.5 && index < 24.9) {
               pointsTextView.text = "100 XP"
               congratsSuggestion.text =  "Your BMI Follows under Normal Range, You are very healthy."
               trophy = 1
            } else if(index >= 25.0 && index < 29.9) {
               congratsLogo.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_oops_emoji))
               congratsTitle.text = "Oops!!"
               pointsTextView.text = "50 XP"
               congratsQuote.text = "You need to Work on Yourself to earn a Fit Badge"
               congratsSuggestion.text =  "Your BMI Follows under OverWeight, You are not alert towards health."
            } else {
               congratsLogo.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_oops_emoji))
               congratsTitle.text = "Oops!!"
               pointsTextView.text = "30 XP"
               congratsQuote.text = "You need to Work on Yourself to earn a Fit Badge"
               congratsSuggestion.text =  "Your BMI Follows under Obesity, You should be more cautious about your health."
            }

        }

        congratsDialogBuilder.setView(congratsScreenBinding.root)
        val congratsDialog = congratsDialogBuilder.create()
        congratsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        congratsDialog.show()
        congratsScreenBinding.coinShowerView.playAnimation()
        congratsScreenBinding.bmiCongratsContinueButton.setOnClickListener {
            congratsDialog.dismiss()
            if(trophy == 1) {
                showScratchDialogBox()
            }
        }
    }


    private fun showScratchDialogBox() {
        val scratchDialogBuilder = AlertDialog.Builder(requireContext())
        val trophyScratchLayoutBinding = TrophyScratchLayoutBinding.inflate(layoutInflater)

        trophyScratchLayoutBinding.apply {
            confettiShower.setAnimation("result-celebration.json")
            confettiShower.repeatCount = 8
            scratchView.setRevealListener(object : ScratchImageView.IRevealListener {
                override fun onRevealed(iv: ScratchImageView?) {
                    trophyScratchLayoutBinding.confettiShower.playAnimation()
                }

                override fun onRevealPercentChangedListener(siv: ScratchImageView?, percent: Float) {
                   if(percent > 0.5) {
                       siv?.reveal()
                   }
                }

            })
        }

        scratchDialogBuilder.setView(trophyScratchLayoutBinding.root)
        val scratchDialog = scratchDialogBuilder.create()
        scratchDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        scratchDialog.show()

    }



}
