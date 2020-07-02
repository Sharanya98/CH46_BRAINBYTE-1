package com.brainbyte.healthareana.util.user

import com.brainbyte.healthareana.data.local.UserDao
import com.brainbyte.healthareana.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.brainbyte.healthareana.data.model.Result
import com.brainbyte.healthareana.data.model.Result.Success
import com.brainbyte.healthareana.data.model.Result.Error
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 */
@Singleton
class UserManager @Inject constructor (
    private val storage: UserDao,
    private val userComponentFactory: UserComponent.Factory
    ) {

    var userComponent: UserComponent? = null
        private set

    fun isUserLoggedIn() = userComponent != null

    suspend fun isUserRegistered(): Result<User> {
       return getCurrentUser()
    }

    private suspend fun getCurrentUser(): Result<User> {
        return withContext(Dispatchers.IO){
            try {
                val user = storage.getUser()
                return@withContext Success(user)
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }
    }

    suspend fun loginUser(auth: FirebaseAuth, account: GoogleSignInAccount): Result<User> {
        firebaseAuthWithGoogle(auth, account.idToken!!)
        delay(2000)
        return if (auth.currentUser != null) {

                if (account.id != null) {

                    val user = User(
                        account.id!!,
                        account.displayName,
                        account.email,
                        account.photoUrl.toString()
                    )

                    withContext(Dispatchers.IO) {
                        storage.insertUser(user)
                    }

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

    fun logout(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient): Result<Boolean> {
        //Firebase signOut
        auth.signOut()

        var result: Result<Boolean> = Error(Exception("Function not Executed!!"))

        //Google SignOut
        googleSignInClient.signOut().addOnCompleteListener {
            if (it.isSuccessful) {
                result = Success(true)
            }
        }

        return result
    }

    private fun firebaseAuthWithGoogle (auth: FirebaseAuth, id: String) {
        //Getting Sign In Credential from google sign in api
        val credential = GoogleAuthProvider.getCredential(id, null)
//         Sign User into firebase
         auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val user = auth.currentUser
                    Timber.d("User name : ${user?.displayName}")
                } else {
                    Timber.e(it.exception)
                }
            }

    }

    private fun userJustLoggedIn() {
        //This keeps data till the user is logged in.
        userComponent = userComponentFactory.create()
    }
}