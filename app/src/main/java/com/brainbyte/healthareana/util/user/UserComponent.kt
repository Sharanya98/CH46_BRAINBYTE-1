package com.brainbyte.healthareana.util.user

import com.brainbyte.healthareana.ui.splash.FragmentSplash
import dagger.Subcomponent


@LoggedUserScope
@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

    fun inject(fragmentSplash: FragmentSplash)

}