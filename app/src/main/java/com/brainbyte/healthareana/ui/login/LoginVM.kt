package com.brainbyte.healthareana.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brainbyte.healthareana.data.model.Result
import com.brainbyte.healthareana.data.model.Result.Success
import com.brainbyte.healthareana.data.model.Result.Error
import com.brainbyte.healthareana.util.user.UserDataRepository
import com.brainbyte.healthareana.util.user.UserManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class LoginVM @Inject constructor(private val userManager: UserManager) : ViewModel() {

    private val _status = MutableLiveData(LoginState.NOT_LOGGED_IN)
    val status: LiveData<LoginState> = _status

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loginInUser(account: GoogleSignInAccount) {
        _loading.value = true

        viewModelScope.launch {
            val result = userManager.loginUser(account)
            if(result is Success) {
                _status.value = LoginState.LOGGED_IN
            } else if(result is Error){
                Timber.d(result.exception)
                _status.value = LoginState.LOGIN_ERROR
            }
        }
    }


}


enum class LoginState {
    NOT_LOGGED_IN,
    LOGGED_IN,
    LOGIN_ERROR
}