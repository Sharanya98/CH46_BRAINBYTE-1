package com.brainbyte.healthareana.ui.sobriety

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.HealthArenaApplication
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentAddictionsBinding
import com.brainbyte.healthareana.databinding.ItemAddictionBinding

class FragmentAddictionSelection : Fragment() {
    private lateinit var binding: FragmentAddictionsBinding


    private lateinit var selectionAdapter: SelectionAdapter
    private fun changeData(index: Int) {
        val newList =
            (requireActivity().application as HealthArenaApplication).addictionStorage.addictions.toMutableList()
                .apply {
                    set(index, get(index).copy(isSelected = !get(index).isSelected))
                }

        (requireActivity().application as HealthArenaApplication).addictionStorage.addictions =
            newList
        selectionAdapter.submitList(newList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddictionsBinding.inflate(inflater, container, false)
        selectionAdapter = SelectionAdapter(object : SelectionAdapter.ClickHandler {
            override fun selectAddiction(index: Int) {
                changeData(index)
            }
        }).apply {
            submitList((requireActivity().application as HealthArenaApplication).addictionStorage.addictions)
        }

        binding.apply {
            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = selectionAdapter
                fab.setOnClickListener { findNavController().navigate(R.id.timeCostFragment) }
            }
            return binding.root
        }
    }
}

class SelectionAdapter(private val clickHandler: ClickHandler) :
    ListAdapter<Model, SelectionVH>(CALLBACK) {
    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Model>() {
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean =
                oldItem.isSelected == newItem.isSelected
        }
    }

    interface ClickHandler {
        fun selectAddiction(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionVH =
        SelectionVH(
            ItemAddictionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SelectionVH, position: Int) {
        holder.bind(getItem(position), clickHandler)
    }

}

class SelectionVH(
    private val binding: ItemAddictionBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Model, clickHandler: SelectionAdapter.ClickHandler) = binding.apply {
        cardView.setOnClickListener { clickHandler.selectAddiction(bindingAdapterPosition) }
        label.text = model.title
        cardView.setCardBackgroundColor(
            Color.parseColor(
                if (!model.isSelected) "#74EBD5"
                else listOf(
                    "#FF6998",
                    "#CC97EC",
                    "#E0E718"
                ).random()
            )
        )

        icon.setImageDrawable(ContextCompat.getDrawable(icon.context, model.icon))
    }
}

data class Model(
    val title: String,
    @DrawableRes val icon: Int,
    val isSelected: Boolean = false
)