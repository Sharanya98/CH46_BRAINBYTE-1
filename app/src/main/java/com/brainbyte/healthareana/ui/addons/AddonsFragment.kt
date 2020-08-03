package com.brainbyte.healthareana.ui.addons

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentAddonsBinding
import com.brainbyte.healthareana.databinding.ItemAddonBinding

class AddonsFragment : Fragment() {
    private lateinit var binding: FragmentAddonsBinding

    private val addons = listOf(
        AddonModel("AI-ChatBot", R.drawable.ic_app_logo),
        AddonModel("Test App 1", R.drawable.ic_app_logo),
        AddonModel("Test App 2", R.drawable.ic_app_logo),
        AddonModel("Test App 3", R.drawable.ic_app_logo),
        AddonModel("Test App 4", R.drawable.ic_app_logo)
    )

    private val addonAdapter = AddonAdapter(object : AddonAdapter.clickHandler {
        override fun onClick() {
        }
    }).apply { submitList(addons) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddonsBinding.inflate(inflater, container, false)
        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = addonAdapter
            }
        }
        return binding.root
    }


}

data class AddonModel(val title: String, @DrawableRes val icon: Int)

class AddonAdapter(private val cHandler: clickHandler) :
    ListAdapter<AddonModel, AddonVH>(CALLBACK) {
    interface clickHandler {
        fun onClick()
    }

    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<AddonModel>() {
            override fun areItemsTheSame(oldItem: AddonModel, newItem: AddonModel): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: AddonModel, newItem: AddonModel): Boolean =
                true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonVH = AddonVH(
        ItemAddonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AddonVH, position: Int) {
        holder.bind(getItem(position), cHandler)
    }
}

class AddonVH(private val binding: ItemAddonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: AddonModel, cHandler: AddonAdapter.clickHandler) = binding.apply {
        addonTitle.text = model.title
        addonLogo.setImageDrawable(ContextCompat.getDrawable(addonLogo.context, model.icon))
        addonActionButton.setOnClickListener { cHandler.onClick() }
    }
}