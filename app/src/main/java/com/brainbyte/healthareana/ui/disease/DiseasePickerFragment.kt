package com.brainbyte.healthareana.ui.disease

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.databinding.FragmentDiseasePickerBinding
import com.brainbyte.healthareana.databinding.ItemDiseaseBinding
import com.brainbyte.healthareana.ui.disease.DiseasePickerAdapter.CLickHandler

class DiseasePickerFragment : Fragment() {
    private lateinit var binding: FragmentDiseasePickerBinding

    private var listOfDisease = listOf(
        Disease("Diabetes"),
        Disease("Artheritis"),
        Disease("Spondylitis"),
        Disease("Asthama"),
        Disease("Down Syndrome"),
        Disease("Tuberculosis"),
        Disease("Cancer"),
        Disease("AIDS")
    )
    private val diseasePickerAdapter = DiseasePickerAdapter(object : CLickHandler {
        override fun selectDisease(index: Int) = toggleDiseaseSelection(index)
    }).apply {
        submitList(listOfDisease)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiseasePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.apply {
                adapter = diseasePickerAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
            fab.setOnClickListener {
                findNavController().navigate(
                    DiseasePickerFragmentDirections.actionDiseasePickerFragmentToFamilyTreeFragment()
                )
            }
        }
    }

    private fun toggleDiseaseSelection(index: Int) {
        val newList = listOfDisease.toMutableList().apply {
            set(index, get(index).copy(picked = !get(index).picked))
        }
        listOfDisease = newList
        diseasePickerAdapter.submitList(newList)
    }
}

data class Disease(val title: String, val picked: Boolean = false)

class DiseasePickerAdapter(private val cLickHandler: CLickHandler) :
    ListAdapter<Disease, DiseaseVH>(CALLBACK) {
    interface CLickHandler {
        fun selectDisease(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseVH = DiseaseVH(
        ItemDiseaseBinding.inflate(LayoutInflater.from(parent.context), parent, false), cLickHandler
    )

    override fun onBindViewHolder(holder: DiseaseVH, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Disease>() {
            override fun areItemsTheSame(oldItem: Disease, newItem: Disease): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Disease, newItem: Disease): Boolean =
                oldItem.picked == newItem.picked
        }
    }
}

class DiseaseVH(private val binding: ItemDiseaseBinding, private val cLickHandler: CLickHandler) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Disease) = binding.apply {
        diseaseTitle.text = model.title
        cardView.setCardBackgroundColor(
            Color.parseColor(
                if (!model.picked) "#74EBD5"
                else listOf(
                    "#FF6998",
                    "#CC97EC",
                    "#E0E718"
                ).random()
            )
        )
        fab.setOnClickListener { cLickHandler.selectDisease(bindingAdapterPosition) }
    }
}