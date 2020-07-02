package com.brainbyte.healthareana.ui.login

import dagger.Subcomponent


@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(fragmentLogin: FragmentLogin)
    
}