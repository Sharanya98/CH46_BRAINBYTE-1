package com.brainbyte.healthareana.di

import android.content.Context
import com.brainbyte.healthareana.data.model.User
import com.brainbyte.healthareana.ui.login.LoginComponent
import com.brainbyte.healthareana.util.user.UserComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, RoomModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory

    fun userComponent(): UserComponent.Factory
}