package com.brainbyte.healthareana.ui.home

import android.graphics.Typeface
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
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentBaseBinding
import com.brainbyte.healthareana.databinding.ItemScoreBinding
import com.brainbyte.healthareana.util.Truss

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

    // TODO:: insert data idhar
    private val listOfScore = listOf(
        ScoreModel(
            R.drawable.ic_bmi_calculator,
            "BMI",
            240,
            123,
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
        binding.apply {
            playerName.setOnClickListener { findNavController().navigate(FragmentHomeDirections.actionFragmentHomeToFragmentSuggestions()) }
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
            }
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