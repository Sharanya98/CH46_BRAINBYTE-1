package com.brainbyte.healthareana.ui.suggestions

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentPolicySuggestionBinding
import com.brainbyte.healthareana.databinding.ItemPolicyBinding
import com.brainbyte.healthareana.util.Truss

class FragmentSuggestions : Fragment() {
    private lateinit var binding: FragmentPolicySuggestionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPolicySuggestionBinding.inflate(
            inflater, container, false
        )
        setUpData()
        return binding.root
    }

    val policies = mutableListOf<SuggestionModel>().apply {
        repeat(8) {
            if (it != 0)
                add(
                    SuggestionModel(
                        "Insure your family $it",
                        123 * it,
                        324 * it,
                        it,
                        0, false,
                        123 * it / 2

                    )
                )
        }
    }

    private fun setUpData() {
        binding.recyclerView.apply {
            adapter = PolicyAdapter(requireContext()).apply {
                submitList(policies)
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}

data class SuggestionModel(
    val title: String,
    val premium: Int,
    val assuredSum: Int,
    val policyTerm: Int,
    val coins: Int,
    val isUsingCoins: Boolean,
    val reducedPremium: Int
) {
    companion object {
        const val totalHealthPoints = 4354
    }
}


class PolicyAdapter(private val context: Context) :
    ListAdapter<SuggestionModel, PolicyViewHolder>(CALLBACK) {
    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<SuggestionModel>() {
            override fun areItemsTheSame(
                oldItem: SuggestionModel,
                newItem: SuggestionModel
            ): Boolean = oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: SuggestionModel,
                newItem: SuggestionModel
            ): Boolean = oldItem.coins == newItem.coins
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder =
        PolicyViewHolder(
            ItemPolicyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )


    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PolicyViewHolder(private val binding: ItemPolicyBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    private fun expand(isChecked: Boolean) {
        val set = ConstraintSet().apply {
            apply {
                if (isChecked) expand()
                else collapse()
            }
            TransitionManager.beginDelayedTransition(binding.root)
            applyTo(binding.root)
        }
    }

    private fun ConstraintSet.collapse() {
        clone(context, R.layout.item_policy)
        clear(R.id.bottom_card_view, ConstraintSet.TOP)
        clear(R.id.bottom_card_view, ConstraintSet.BOTTOM)
        connect(
            R.id.bottom_card_view,
            ConstraintSet.TOP,
            R.id.top_card_view,
            ConstraintSet.TOP
        )
        connect(
            R.id.bottom_card_view,
            ConstraintSet.BOTTOM,
            R.id.top_card_view,
            ConstraintSet.BOTTOM
        )

    }

    private fun ConstraintSet.expand() {
        clone(context, R.layout.item_policy)
        clear(R.id.bottom_card_view, ConstraintSet.TOP)
        clear(R.id.bottom_card_view, ConstraintSet.BOTTOM)
        connect(
            R.id.bottom_card_view,
            ConstraintSet.TOP,
            R.id.top_card_view,
            ConstraintSet.BOTTOM
        )
        connect(
            R.id.bottom_card_view,
            ConstraintSet.BOTTOM,
            R.id.parent_layout,
            ConstraintSet.BOTTOM
        )
    }

    fun bind(model: SuggestionModel) = binding.apply {
        isUsingCoinsView.setOnCheckedChangeListener { _, isChecked ->
            expand(isChecked)
        }
        policyTitle.text = model.title
        premiumTextView.text =
            Truss()
                .pushSpan(ForegroundColorSpan(Color.WHITE))
                .append("Premium\n")
                .popSpan()
                .pushSpan(ForegroundColorSpan(Color.parseColor("#331E68")))
                .append("Rs ${model.premium}")
                .build()
        sumAssuredTextView.text = Truss()
            .pushSpan(ForegroundColorSpan(Color.WHITE))
            .append("Sum assured:")
            .popSpan()
            .pushSpan(ForegroundColorSpan(Color.parseColor("#331E68")))
            .append(model.assuredSum)
            .build()
        policyTermPeriodText.text = Truss()
            .pushSpan(ForegroundColorSpan(Color.WHITE))
            .append("Policy term : ")
            .popSpan()
            .pushSpan(ForegroundColorSpan(Color.parseColor("#331E68")))
            .append("${model.policyTerm} Years")
            .build()
        pointsCountText.text = "  ${SuggestionModel.totalHealthPoints}"
        chipCoinCount.text = "${model.coins}"
        reducedPremiumText.text = "Premium\nRs ${model.premium / 3}"
    }
}