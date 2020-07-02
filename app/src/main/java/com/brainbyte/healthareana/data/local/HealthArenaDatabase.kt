package com.brainbyte.healthareana.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brainbyte.healthareana.data.model.User


@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class HealthArenaDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}