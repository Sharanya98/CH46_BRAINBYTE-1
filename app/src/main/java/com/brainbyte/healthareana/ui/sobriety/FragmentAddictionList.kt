package com.brainbyte.healthareana.ui.sobriety

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.brainbyte.healthareana.HealthArenaApplication
import com.brainbyte.healthareana.databinding.FragmentAddictionListBinding
import com.brainbyte.healthareana.databinding.ItemAddictionStatBinding
import java.util.*

class FragmentAddictionList : Fragment() {

    private lateinit var binding: FragmentAddictionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddictionListBinding.inflate(inflater, container, false)
        val list =
            (requireActivity().application as HealthArenaApplication).addictionStorage.addictions
                .map {
                    AddictionTimeModel(
                        it.title,
                        Random().nextInt(100),
                        it.icon
                    )
                }
        binding.recyclerView.apply {
            adapter = AddictionAdapter().apply {
                submitList(list.also {
                    Log.d("ListAddiction", "onCreateView: ${it.size}")
                }
                )
            }
            layoutManager = LinearLayoutManager(this@FragmentAddictionList.context)

        }
        return binding.root
    }
}

class AddictionAdapter : ListAdapter<AddictionTimeModel, AddictionStatVH>(CALLBACK) {
    private companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<AddictionTimeModel>() {
            override fun areItemsTheSame(
                oldItem: AddictionTimeModel,
                newItem: AddictionTimeModel
            ): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: AddictionTimeModel,
                newItem: AddictionTimeModel
            ): Boolean =
                true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddictionStatVH =
        AddictionStatVH(
            ItemAddictionStatBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AddictionStatVH, position: Int) {
        holder.bind(getItem(position))
    }

}

class AddictionStatVH(
    private val binding: ItemAddictionStatBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(model: AddictionTimeModel) = binding.apply {
        iconImageView.setImageDrawable(ContextCompat.getDrawable(iconImageView.context, model.icon))
        timeSinceLastReplace.text = "${model.days} Days"
        cardView.setCardBackgroundColor(
            Color.parseColor(
                listOf(
                    "#74EBD5",
                    "#FF6998",
                    "#CC97EC",
                    "#E0E718",
                    "#F9B25D"
                ).random()
            )
        )
    }
}

data class AddictionTimeModel(
    val title: String,
    val days: Int,
    @DrawableRes val icon: Int
)