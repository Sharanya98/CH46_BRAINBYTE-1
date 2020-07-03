package com.brainbyte.healthareana.di

import android.content.Context
import com.brainbyte.healthareana.ui.login.LoginComponent
import com.brainbyte.healthareana.util.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, RoomModule::class, FirebaseModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun userManager(): UserManager

    fun loginComponent(): LoginComponent.Factory

}