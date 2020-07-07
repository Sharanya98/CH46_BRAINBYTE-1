package com.brainbyte.healthareana.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
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
import com.brainbyte.healthareana.data.model.Result
import com.brainbyte.healthareana.data.model.Result.Error
import com.brainbyte.healthareana.data.model.Result.Success
import com.brainbyte.healthareana.data.model.User
import com.brainbyte.healthareana.databinding.FragmentLoginBinding
import com.brainbyte.healthareana.util.USER_SP_KEY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FragmentLogin : Fragment() {

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 9001
    private lateinit var userManager: UserManager
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let {
            userManager = UserManager(it.getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE))
        }
        if (userManager.isUserLoggedIn()) navigateToHome()

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestIdToken(resources.getString(R.string.web_server_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        binding.apply {
            buttonSignIn.apply {
                setOnClickListener { signIn() }
            }
            appIcon.setOnClickListener { animateCircleRevel() }
        }
        return binding.root
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
                    doOnStart { visibility = android.view.View.VISIBLE }
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
                override fun onTransitionEnd(transition: Transition) = showAppName()
                override fun onTransitionResume(transition: Transition) = Unit
                override fun onTransitionPause(transition: Transition) = Unit
                override fun onTransitionCancel(transition: Transition) = Unit
                override fun onTransitionStart(transition: Transition) = Unit
            })
        }
        TransitionManager.beginDelayedTransition(binding.root, transition)
        constraintSet.applyTo(binding.root)
    }

    private fun showAppName() {
        val titleAnimation = with(binding.titleTextView) {
            ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
                doOnStart { visibility = View.VISIBLE }
            }
        }

        val hintAnimation = with(binding.loginHintTextView) {
            ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
                doOnStart { visibility = View.VISIBLE }
            }
        }
        val buttonAnimation = with(binding.buttonSignIn) {
            ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).apply {
                doOnStart { visibility = View.VISIBLE }
            }
        }
        AnimatorSet().apply {
            duration = ANIMATION_DURATION
            playTogether(
                titleAnimation, hintAnimation, buttonAnimation
            )
            start()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        Timber.d("Sign in Called!!")
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (RC_SIGN_IN == requestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                Timber.d("account details : ${account?.displayName}")
                account?.let {
                    val result = loginUser(it)
                    if (result is Success) {
                        userManager.saveAccount(result.data)
                        findNavController().navigate(FragmentLoginDirections.actionFragmentLoginToFragmentUserNameSelect())
                    }

                }

            } catch (e: ApiException) {
            }
        }
    }

    private fun navigateToHome() = findNavController().navigate(R.id.fragmentHome)


    private fun loginUser(account: GoogleSignInAccount): Result<User> {

        Timber.i("Login called!!")
        firebaseAuthWithGoogle(auth, account.idToken!!)
        runBlocking { delay(2000) }
        return if (auth.currentUser != null) {
            if (account.id != null) {

                val user = User(
                    account.id!!,
                    account.displayName,
                    account.email,
                    account.photoUrl.toString()
                )
                Success(user)
            } else {
                Timber.e(Exception("Invalid Id Params"))
                Error(Exception("Invalid Id Params"))
            }
        } else {
            Timber.e(Exception("Firebase Error, Account was unable to Login in!!"))
            Error(Exception("Firebase Error, Account was unable to Login in!!"))
        }
    }

    private fun firebaseAuthWithGoogle(auth: FirebaseAuth, id: String) {

        // Getting Sign In Credential from google sign in api
        val credential = GoogleAuthProvider.getCredential(id, null)

        // Sign User into firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("User name : ${user?.displayName}")
                } else {
                    Timber.e(it.exception)
                }
            }

    }

    fun logout(): Result<Boolean> {
        Timber.i("Logout called!!")
        //Firebase signOut
        auth.signOut()
        var result: Result<Boolean> = Error(Exception("Function not Executed!!"))

        //Google SignOut
        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                result = Success(true)
            }
        }
        runBlocking { delay(1000) }
        return result
    }

    private companion object {
        const val ANIMATION_DURATION = 2000L
    }
}