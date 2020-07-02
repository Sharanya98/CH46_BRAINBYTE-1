package com.brainbyte.healthareana.util.user

import dagger.Subcomponent

@Subcomponent
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserComponent
    }

}