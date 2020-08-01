package com.brainbyte.healthareana.ui.power_genes

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentFamilyTreeBinding
import com.brainbyte.healthareana.databinding.TrophyScratchLayoutBinding
import com.cooltechworks.views.ScratchImageView
import kotlinx.coroutines.delay

class FamilyTreeFragment : Fragment() {
    private lateinit var binding: FragmentFamilyTreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyTreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFamily.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.type = "text/plain"
            shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.github.com")
            shareIntent.setPackage("com.whatsapp")
            startActivity(shareIntent)

            binding.apply {
                daughterIcon.visibility = View.VISIBLE
                sonIcon.visibility = View.VISIBLE
                verticalBarView.visibility = View.VISIBLE
                horizontalBarView2.visibility = View.VISIBLE
            }

            showScratchDialogBox()
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
            bmiCongratsContinueButton.setOnClickListener {
                findNavController().navigate(R.id.fragmentHome)
            }

        }

        scratchDialogBuilder.setView(trophyScratchLayoutBinding.root)
        val scratchDialog = scratchDialogBuilder.create()
        scratchDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        scratchDialog.show()

        trophyScratchLayoutBinding.bmiCongratsContinueButton.setOnClickListener {
            findNavController().navigate(R.id.fragmentHome)
            scratchDialog.dismiss()

        }

    }

}