package com.brainbyte.healthareana.ui.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.databinding.FragmentQuestionsBinding
import com.brainbyte.healthareana.databinding.ItemQuestionBinding

class QuestionsFragment : Fragment() {
    private lateinit var binding: FragmentQuestionsBinding
    private val list = mutableListOf<QuestionScreen>().apply {
        repeat(10) {
            add(QuestionScreen("Question $it", "Q$it option 1", "Q$it option 2"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionsBinding.inflate(layoutInflater, container, false)
        binding.viewPager2.apply {
            adapter = QuestionAdapter().apply { submitList(list) }
        }
        return binding.root
    }
}

data class QuestionScreen(
    val question: String,
    val option1: String,
    val option2: String
)

class QuestionScreenViewHolder(private val binding: ItemQuestionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: QuestionScreen) = binding.apply {
        questionText.text = model.question
        option1Button.text = model.option1
        option2Button.text = model.option2
    }
}

class QuestionAdapter : ListAdapter<QuestionScreen, QuestionScreenViewHolder>(CALLBACK) {
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
        holder.bind(getItem(position))
    }
}