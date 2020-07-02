package com.brainbyte.healthareana.di

import android.content.Context
import androidx.room.Room
import com.brainbyte.healthareana.data.local.HealthArenaDatabase
import com.brainbyte.healthareana.data.local.UserDao
import com.brainbyte.healthareana.util.HEALTH_ARENA_DB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    private lateinit var healthArenaDatabase: HealthArenaDatabase

    @Singleton
    @Provides
    fun provideYearBookDb(context: Context): HealthArenaDatabase{
        healthArenaDatabase = Room.databaseBuilder(
            context,
            HealthArenaDatabase::class.java,
            HEALTH_ARENA_DB
        ).build()

        return healthArenaDatabase
    }

    @Provides
    fun provideUserDao(db: HealthArenaDatabase): UserDao = db.userDao()
}
