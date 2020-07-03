package com.brainbyte.healthareana.ui.login

import com.brainbyte.healthareana.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(fragmentLogin: FragmentLogin)
}