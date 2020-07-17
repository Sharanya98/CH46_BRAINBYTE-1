package com.brainbyte.healthareana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class User (
    val id: String,
    val name: String?,
    val email: String?,
    val profilePhotoUrl: String?
)