package com.brainbyte.healthareana.ui.sobriety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentAddictionsBinding
import com.brainbyte.healthareana.databinding.ItemAddictionBinding

class FragmentAddictionSelection : Fragment() {
    private lateinit var binding: FragmentAddictionsBinding

    private val addictions = listOf(
        Model("Smoking", R.drawable.ic_baseline_arrow_forward_24),
        Model("Drinking", R.drawable.ic_baseline_arrow_forward_24),
        Model("Drugs", R.drawable.ic_baseline_arrow_forward_24),
        Model("T.V", R.drawable.ic_baseline_arrow_forward_24),
        Model("Video Games", R.drawable.ic_baseline_arrow_forward_24),
        Model("Fast Food", R.drawable.ic_baseline_arrow_forward_24),
        Model("Social Media", R.drawable.ic_baseline_arrow_forward_24),
        Model("Shopping", R.drawable.ic_baseline_arrow_forward_24)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddictionsBinding.inflate(inflater, container, false)
        binding.apply {
            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = SelectionAdapter(addictions)
            }
            fab.setOnClickListener { findNavController().navigate(R.id.timeCostFragment) }
        }
        return binding.root
    }
}

class SelectionAdapter(private val list: List<Model>) : RecyclerView.Adapter<SelectionVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionVH = SelectionVH(
        ItemAddictionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SelectionVH, position: Int) {
        holder.bind(list[position])
    }

}

class SelectionVH(private val binding: ItemAddictionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Model) = binding.apply {
        label.text = model.title
        icon.setImageDrawable(ContextCompat.getDrawable(icon.context, model.icon))
    }
}

data class Model(
    val title: String,
    @DrawableRes val icon: Int
)