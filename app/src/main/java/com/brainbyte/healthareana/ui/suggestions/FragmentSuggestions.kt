package com.brainbyte.healthareana.ui.suggestions

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.data.local.UserManager
import com.brainbyte.healthareana.databinding.FragmentPolicySuggestionBinding
import com.brainbyte.healthareana.databinding.ItemPolicyBinding
import com.brainbyte.healthareana.databinding.PopSuggestionConnectBinding
import com.brainbyte.healthareana.util.Truss
import com.brainbyte.healthareana.util.USER_SP_KEY

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

    private val policies = mutableListOf<SuggestionModel>()

    private fun showContactPopUp() {
        val contactDialogBuilder = AlertDialog.Builder(requireContext())
        val contactPopBinding = PopSuggestionConnectBinding.inflate(layoutInflater)
        contactDialogBuilder.setView(contactPopBinding.root)

        val contactDialog = contactDialogBuilder.create()
        contactDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        contactDialog.show()
    }


    inner class ClickHandler {
        fun connect () {
            showContactPopUp()
        }
    }




    private fun setUpData() {

        val userManager = UserManager(requireContext().getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE))

        val user = userManager.getLoggedInUser()
        val truss = Truss()

        if(user.email == "vaidyadevanshu@gmail.com") {
                 truss.append("Hey Sharanya,\nYour")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append(" BMI score")
                .popSpan()
                .append(" is great however your ")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append("power genes ")
                .popSpan()
                .append("data indicates that you are at risk for ")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append("diabetes.\n")
                .popSpan()
                .append("We would strongly recommend you to choose the suggested policy as it is best suited according to your age and income and it will provide good risk coverage for you as well as for your ")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append(" critical illnesses.")

            policies.add(SuggestionModel("Family Floater Health insurance", 30000, 500000,4,1000, false, 29000))
            policies.add(SuggestionModel("Critical Illness Health cover", 60000, 1000000,4,1000, false, 59000))

        } else {

            truss.append("Hey Aditya,\nYour")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append(" overall heath progress")
                .popSpan()
                .append(" is great. However, we are concerned regarding your ")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append("age ")
                .popSpan()
                .append("At this age health issues arise that involve ")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append("expensive ")
                .popSpan()
                .append("treatments and in order to cover such a high")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append(" medical cost ")
                .popSpan()
                .append("We would strongly recommend you to choose the suggested")
                .pushSpan(ForegroundColorSpan(Color.RED))
                .append(" senior citizen")
                .popSpan()
                .append(" health cover policies.\nIf you are previously ")
                .pushSpan(ForegroundColorSpan(Color.BLACK))
                .append(" insured ")
                .popSpan()
                .append("then we would recommend you to ")
                .pushSpan(ForegroundColorSpan(Color.BLACK))
                .append("customise ")
                .popSpan()
                .append("your health insurance using the ")
                .pushSpan(ForegroundColorSpan(Color.BLACK))
                .append("top up ")
                .popSpan()
                .append("health insurance policy.")


            policies.add(SuggestionModel("Senior Citizen Health insurance", 30000, 500000,4,1000, false, 29000))
            policies.add(SuggestionModel("Top up Health insurance", 60000, 1000000,4,1000, false, 59000))
        }


        binding.apply {

            subtitleTextView.text = truss.build()
        }
        binding.recyclerView.apply {
            adapter = PolicyAdapter(requireContext(), ClickHandler()).apply {
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


class PolicyAdapter(private val context: Context, private val handler: FragmentSuggestions.ClickHandler) :
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
        holder.bind(getItem(position), handler)
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

    fun bind(model: SuggestionModel, handler: FragmentSuggestions.ClickHandler) = binding.apply {
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

        connectButton.setOnClickListener {
            handler.connect()
        }

    }




}