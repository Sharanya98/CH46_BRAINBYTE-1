package com.brainbyte.healthareana.ui.gender

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.databinding.FragmentGenderBinding
import timber.log.Timber

class FragmentGender : Fragment() {

    private lateinit var binding: FragmentGenderBinding

    private var isMale = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenderBinding.inflate(layoutInflater, container, false)

        val femaleAnimDrawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.avd_female_to_male_anim
        )
        val maleAnimDrawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.avd_male_to_female_anim
        )

        femaleAnimDrawable?.registerAnimationCallback(object :
            Animatable2Compat.AnimationCallback() {
            override fun onAnimationStart(drawable: Drawable?) {
                binding.genderChangeButton.visibility = View.GONE
            }

            override fun onAnimationEnd(drawable: Drawable?) {
                binding.genderChangeButton.visibility = View.VISIBLE
                (drawable as AnimatedVectorDrawable).reset()
                binding.genderImage.setImageDrawable(maleAnimDrawable)
            }
        })

        maleAnimDrawable?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationStart(drawable: Drawable?) {
                binding.genderChangeButton.visibility = View.GONE
            }

            override fun onAnimationEnd(drawable: Drawable?) {
                binding.genderChangeButton.visibility = View.VISIBLE
                (drawable as AnimatedVectorDrawable).reset()
                binding.genderImage.setImageDrawable(femaleAnimDrawable)
            }
        })


        binding.genderImage.setImageDrawable(femaleAnimDrawable)

        binding.genderChangeButton.setOnClickListener {
            binding.apply {
                if (isMale) {

                    (genderChangeButton.drawable as VectorDrawable)
                        .setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.color_gender_male
                            )
                        )

                    (genderImage.drawable as Animatable).start()

                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.color_gender_female
                        )
                    )
                    isMale = false
                    Timber.d("Value of isMale in if = $isMale")
                } else {

                    (genderChangeButton.drawable as VectorDrawable)
                        .setTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.color_gender_female
                            )
                        )

                    (genderImage.drawable as Animatable).start()

                    root.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.color_gender_male
                        )
                    )
                    isMale = true
                    Timber.d("Value of isMale in else = $isMale")
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(FragmentGenderDirections.actionFragmentGenderToFragmentHome())
        }
    }

}
