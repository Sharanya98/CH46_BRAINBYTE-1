package com.brainbyte.healthareana.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.brainbyte.healthareana.R
import com.brainbyte.healthareana.data.local.UserManager
import com.brainbyte.healthareana.databinding.FragmentSplashBinding
import com.brainbyte.healthareana.util.USER_SP_KEY

class FragmentSplash : Fragment() {
    private companion object {
        const val ANIMATION_DURATION = 2000L
    }

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.post { animateCircleRevel() }
    }

    private fun animateCircleRevel() {
        with(binding.circularRevelView) {
            val cx = width / 2
            val cy = height / 2
            val finalRadius = kotlin.math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            android.view.ViewAnimationUtils.createCircularReveal(
                binding.circularRevelView,
                cx,
                cy,
                0f,
                finalRadius
            )
                .apply {
                    duration = ANIMATION_DURATION
                    interpolator = android.view.animation.AccelerateInterpolator()
                    doOnStart { visibility = View.VISIBLE }
                    doOnEnd { animateMoveIcon() }
                    start()
                }
        }
    }

    private fun animateMoveIcon() {
        val constraintSet = ConstraintSet().apply {
            clone(binding.root)
            clear(R.id.app_icon, ConstraintSet.BOTTOM)
        }
        val transition = ChangeBounds().apply {
            duration = ANIMATION_DURATION
            addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    context?.let {
                        val userManager =
                            UserManager(it.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE))
                        if (userManager.isUserLoggedIn()) findNavController().navigate(R.id.fragmentBMI)
                        else findNavController().navigate(R.id.fragmentLogin)
                    }
                }

                override fun onTransitionResume(transition: Transition) = Unit
                override fun onTransitionPause(transition: Transition) = Unit
                override fun onTransitionCancel(transition: Transition) = Unit
                override fun onTransitionStart(transition: Transition) = Unit
            })
        }
        TransitionManager.beginDelayedTransition(binding.root, transition)
        constraintSet.applyTo(binding.root)
    }


}