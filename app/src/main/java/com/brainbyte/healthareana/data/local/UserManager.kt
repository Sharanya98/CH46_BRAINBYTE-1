package com.brainbyte.healthareana.data.local

import android.content.SharedPreferences
import com.brainbyte.healthareana.data.model.User
import com.brainbyte.healthareana.util.USER_EMAIL
import com.brainbyte.healthareana.util.USER_ID
import com.brainbyte.healthareana.util.USER_NAME
import com.brainbyte.healthareana.util.USER_PHOTO_URL

class UserManager(private val sharedPreferences: SharedPreferences) {
    fun saveAccount(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_EMAIL, user.email)
            putString(USER_ID, user.id)
            putString(USER_NAME, user.name)
            putString(USER_PHOTO_URL, user.profilePhotoUrl)
            apply()
        }
    }

    fun isUserLoggedIn(): Boolean = !sharedPreferences.getString(USER_ID, null).isNullOrBlank()

    private fun getLoggedInUser(): User =
        with(sharedPreferences) {
            User(
                id = getString(USER_ID, null)!!,
                name = getString(USER_NAME, null),
                email = getString(USER_EMAIL, null),
                profilePhotoUrl = getString(USER_PHOTO_URL, null)
            )
        }



    fun storeBmiIndex(value: Float, points: Float) {
        sharedPreferences.edit().apply() {
            putFloat("bmi", value)
            putFloat("bmi_points", points)
            apply()
        }
    }

    fun getBmiIndex(): Float {
        return sharedPreferences.getFloat("bmi", 0f)
    }

    fun getBmiPoints(): Float {
        return  sharedPreferences.getFloat("bmi_points", 0f)
    }


    private fun logout(): Boolean =
        with(sharedPreferences.edit()) {
            remove(USER_PHOTO_URL)
            remove(USER_EMAIL)
            remove(USER_NAME)
            remove(USER_ID)
            commit()
        }
}