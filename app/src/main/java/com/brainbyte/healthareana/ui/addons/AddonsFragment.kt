package com.brainbyte.healthareana.ui.addons

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brainbyte.healthareana.ChatBotTest
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentAddonsBinding
import com.brainbyte.healthareana.databinding.ItemAddonBinding
import kotlinx.android.synthetic.main.fragment_addiction_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

val ADDON_KEY = "addon_key"

class AddonsFragment : Fragment() {
    private lateinit var binding: FragmentAddonsBinding

    private var errorEncountered = 0
    private val addons = listOf(
        AddonModel("AI-ChatBot", R.drawable.ic_app_logo),
        AddonModel("Test App 1", R.drawable.ic_app_logo),
        AddonModel("Test App 2", R.drawable.ic_app_logo),
        AddonModel("Test App 3", R.drawable.ic_app_logo),
        AddonModel("Test App 4", R.drawable.ic_app_logo)
    )

    private val addonAdapter = AddonAdapter(object : AddonAdapter.clickHandler {
        override fun onClick(index: Int) {
            if (index == 0) {
                if (requireActivity().getSharedPreferences(
                        resources.getString(R.string.app_name),
                        Context.MODE_PRIVATE
                    ).getBoolean(ADDON_KEY, false)
                ) {
                    startActivity(Intent(requireActivity(), ChatBotTest::class.java))
                }
                lifecycle.coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        binding.apply {
                            progressCircular.visibility = View.VISIBLE
                            statusIndicator.text = "Fetching Addon..."
                            delay(4000)
                            if (errorEncountered == 0) {
                                progressCircular.visibility = View.GONE
                                statusIndicator.text = "Error detected. Check your network!"
                                errorEncountered++
                            } else {
                                progressCircular.visibility = View.GONE
                                statusIndicator.text = "Success"
                                Toast.makeText(
                                    requireActivity(),
                                    "Launching Addon",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        ChatBotTest::class.java
                                    )
                                )
                                requireActivity().getSharedPreferences(
                                    resources.getString(R.string.app_name),
                                    Context.MODE_PRIVATE
                                ).edit().apply {
                                    putBoolean(ADDON_KEY, true)
                                    apply()
                                }
                            }
                        }
                    }
                }
            } else Toast.makeText(
                requireActivity(),
                "Unsupported addon requested",
                Toast.LENGTH_SHORT
            ).show()
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
        fun onClick(index: Int)
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
        addonActionButton.setOnClickListener { cHandler.onClick(absoluteAdapterPosition) }
    }
}