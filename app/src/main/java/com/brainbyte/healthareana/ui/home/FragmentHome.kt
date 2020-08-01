package com.brainbyte.healthareana.ui.home

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.HealthArenaApplication
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.data.local.UserManager
import com.brainbyte.healthareana.databinding.FragmentBaseBinding
import com.brainbyte.healthareana.databinding.ItemScoreBinding
import com.brainbyte.healthareana.databinding.PopIncomeBinding
import com.brainbyte.healthareana.databinding.PopOccupationBinding
import com.brainbyte.healthareana.util.Truss
import com.brainbyte.healthareana.util.USER_SP_KEY

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

    private val listOfScore = listOf(
        ScoreModel(
            R.drawable.ic_bmi_calculator,
            "BMI",
          123,
            100,
            listOf(R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin)
        ),
        ScoreModel(
            R.drawable.ic_setup_footsteps,
            "Step Up",
            240,
            123,
            listOf(R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin)
        ),
        ScoreModel(
            R.drawable.ic_soberity_toast,
            "Sobriety",
            240,
            123,
            listOf(R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin)
        ),
        ScoreModel(
            R.drawable.ic_stress_image,
            "StressOMeter",
            240,
            123,
            listOf(R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin)
        ),
        ScoreModel(
            R.drawable.ic_power_gene_image,
            "Power Genes",
            240,
            123,
            listOf(R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin, R.drawable.ic_coin)
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if((requireActivity().application as HealthArenaApplication).incomePop) {
            (requireActivity().application as HealthArenaApplication).incomePop = false
            showIncomePopUp()
        }

        binding.apply {
            accountIcon.setOnClickListener { findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentQuestions()) }
            profileProgressBar.setProgressWithAnimation(85f, 5000)
            fragmentAccountContainer.recyclerView.apply {
                adapter = ScoreAdapter(listOfScore)
                layoutManager = LinearLayoutManager(requireContext())
            }
            fragmentHomeContainer.apply {
                bmiGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentBMI())
                }

                sobrietyGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToAddictionOnBoarding())
                }

                stepUpGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentStepUp())
                }

                powerGeneGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToDiseaseOnBoardingFragment())
                }

                stressGameButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentStressOMeter())
                }

                suggestionButton.setOnClickListener {
                    findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentSuggestions())
                }
            }
        }

    }


    private fun showIncomePopUp() {
        val incomeDialogBuilder = AlertDialog.Builder(requireContext())
        val incomePopupBinding = PopIncomeBinding.inflate(layoutInflater)

        incomePopupBinding.apply {

        }

        incomeDialogBuilder.setView(incomePopupBinding.root)
        val incomeDialog = incomeDialogBuilder.create()
        incomeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        incomeDialog.show()

        incomePopupBinding.bmiResultContinueButton.setOnClickListener {
            incomeDialog.dismiss()
        }
    }


    private fun showOccupationPopUp() {
        val occupationDialogBuilder = AlertDialog.Builder(requireContext())
        val occupationPopupBinding = PopOccupationBinding.inflate(layoutInflater)

        occupationPopupBinding.apply {

            agriButton.setOnClickListener {
                it.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))

            }

            selfButton.setOnClickListener {

            }

            serviceButton.setOnClickListener {

            }

            govtButton.setOnClickListener {

            }

            businessButton.setOnClickListener {

            }

            retiredButton.setOnClickListener {

            }

        }


        val occupationDialog = occupationDialogBuilder.create()
        occupationDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        occupationDialog.show()
        occupationPopupBinding.bmiResultContinueButton.setOnClickListener {
            occupationDialog.dismiss()
        }

    }

}

data class ScoreModel(
    @DrawableRes val icon: Int,
    val title: String,
    val data: Int,
    val coins: Int,
    val badges: List<Int>
)

class ScoreAdapter(private val list: List<ScoreModel>) : RecyclerView.Adapter<ScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder =
        ScoreViewHolder(
            ItemScoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class ScoreViewHolder(private val binding: ItemScoreBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: ScoreModel) = binding.apply {
        icon.setImageDrawable(ContextCompat.getDrawable(icon.context, model.icon))
        titleText.text = Truss().pushSpan(AbsoluteSizeSpan(18, true))
            .pushSpan(StyleSpan(Typeface.BOLD))
            .append(model.title)
            .popSpan()
            .popSpan()
            .pushSpan(AbsoluteSizeSpan(16, true))
            .append("\n${model.data}")
            .build()
        coinsText.text = model.coins.toString()


        val truss = Truss().append("Badge\n")
        model.badges.forEach {
            val drawable = ContextCompat.getDrawable(badgeTextView.context, it)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }
            truss
                .pushSpan(ImageSpan(drawable!!, ImageSpan.ALIGN_BASELINE))
                .append(" ")
                .popSpan()
                .append(" ")
        }
        badgeTextView.text = truss.build()
    }
}