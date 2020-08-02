package com.brainbyte.healthareana.ui.questions

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentQuestionsBinding
import com.brainbyte.healthareana.databinding.ItemQuestionBinding
import com.brainbyte.healthareana.databinding.StepUpCongratsScreenBinding
import timber.log.Timber

class QuestionsFragment : Fragment() {
    private lateinit var binding: FragmentQuestionsBinding
    private var list = mutableListOf<QuestionScreen>().apply {
        add(QuestionScreen("Do you have an insurance policy?", "Yes", "No", "Yes", null))
        add(
            QuestionScreen(
                "what is policy limit?",
                "Maximum amount paid to the user",
                "Minimum amount paid to the user",
                "Maximum amount paid to the user",
                null
            )
        )
        add(
            QuestionScreen(
                "One must take General Insurance Policies",
                "Agree",
                "Disagree",
                "Agree",
                null
            )
        )
        add(
            QuestionScreen(
                " Which of these long-term savings you are aware of?",
                "Life Insurance",
                "Fixed deposit",
                "Life Insurance",
                null
            )
        )
        add(
            QuestionScreen(
                " Would you suggest insurance policies to your friends?",
                "Yes",
                "No",
                "Yes",
                null
            )
        )
        add(
            QuestionScreen(
                "Do you know what is annual premium? ",
                "amount paid periodically",
                "offered discount",
                "amount paid periodically",
                null
            )
        )
    }


    fun showDialog() {

        val questionDialogBuilder = AlertDialog.Builder(requireContext())
        val stepUpCongratsScreenBinding = StepUpCongratsScreenBinding.inflate(layoutInflater)

        stepUpCongratsScreenBinding.apply {

            congratsQuote.text = "You have answered all questions correctly "

            pointsTextView.text = ""

            congratsSuggestion.text = ""
        }

        questionDialogBuilder.setView(stepUpCongratsScreenBinding.root)
        val questionDialog = questionDialogBuilder.create()
        questionDialog.show()

        stepUpCongratsScreenBinding.apply {
            stepUpContinue.setOnClickListener {
                findNavController().navigate(R.id.fragmentSuggestions)
            }
        }
    }


    private val questionAdapter by lazy {
        QuestionAdapter(object : QuestionAdapter.clickHandler {
            override fun onClick(answer: String, index: Int) {
                setAnswer(index, answer)
                if(index == binding.viewPager2.adapter?.itemCount) {
                    showDialog()
                }

                binding.viewPager2.setCurrentItem(index+1, true)
            }

        }).apply { submitList(list) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(layoutInflater, container, false)
        binding.viewPager2.apply {
            adapter = questionAdapter

            isUserInputEnabled = false
        }
        return binding.root
    }

    fun setAnswer(index: Int, answer: String) {
        val updatedData = list.toMutableList().apply {
            set(index, get(index).copy(userAnswer = answer))
        }
        list = updatedData
        questionAdapter.submitList(updatedData)
    }
}

data class QuestionScreen(
    val question: String,
    val option1: String,
    val option2: String,
    val answer: String,
    val userAnswer: String?
)

class QuestionScreenViewHolder(private val binding: ItemQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: QuestionScreen, handler: QuestionAdapter.clickHandler) = binding.apply {
        questionText.text = model.question
        option1Button.text = model.option1
        option2Button.text = model.option2
        option1Button.setOnClickListener {
            option1Button.setBackgroundColor(if (option1Button.text == model.answer) Color.GREEN else Color.RED)
            handler.onClick(model.option1, absoluteAdapterPosition)
        }
        option2Button.setOnClickListener {
            option2Button.setBackgroundColor(if (option2Button.text == model.answer) Color.GREEN else Color.RED)
            handler.onClick(model.option2, absoluteAdapterPosition)
        }
    }
}

class QuestionAdapter(private val handler: clickHandler) :
    ListAdapter<QuestionScreen, QuestionScreenViewHolder>(CALLBACK) {
    interface clickHandler {
        fun onClick(answer: String, index: Int)
    }

    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<QuestionScreen>() {
            override fun areItemsTheSame(
                oldItem: QuestionScreen,
                newItem: QuestionScreen
            ): Boolean = oldItem.question == newItem.question

            override fun areContentsTheSame(
                oldItem: QuestionScreen,
                newItem: QuestionScreen
            ): Boolean = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionScreenViewHolder =
        QuestionScreenViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: QuestionScreenViewHolder, position: Int) {
        holder.bind(getItem(position), handler)
    }
}