package com.brainbyte.healthareana

import android.app.Application
import com.brainbyte.healthareana.ui.sobriety.Model
import timber.log.Timber

class HealthArenaApplication : Application() {

    var incomePop = true



    val addictionStorage by lazy {
        AddictionStorage()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

class AddictionStorage {
    var addictions = mutableListOf<Model>(
        Model("Smoking", R.drawable.ic_smoking_icon),
        Model("Drinking", R.drawable.ic_wine_glass_icon),
        Model("Drugs", R.drawable.ic_pills_icon),
        Model("T.V", R.drawable.ic_tv_icon),
        Model("Video Games", R.drawable.ic_game_controller_icon),
        Model("Fast Food", R.drawable.ic_food_icon),
        Model("Social Media", R.drawable.ic_mobile_phone_icon),
        Model("Shopping", R.drawable.ic_shopping_cart_icon)
    )


    fun getAllUserAddictions(): List<Model> = listOf<Model>().apply {
        addictions.filter { it.isSelected }
    }
}