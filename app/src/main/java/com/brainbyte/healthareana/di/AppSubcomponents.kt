package com.brainbyte.healthareana.di

import com.brainbyte.healthareana.ui.login.LoginComponent
import com.brainbyte.healthareana.util.user.UserComponent
import dagger.Module

@Module(subcomponents = [LoginComponent::class, UserComponent::class])
interface AppSubcomponents