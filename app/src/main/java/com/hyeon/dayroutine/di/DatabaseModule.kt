package com.hyeon.dayroutine.di

import android.content.Context
import androidx.room.Room
import com.hyeon.dayroutine.data.local.dao.RoutineDao
import com.hyeon.dayroutine.data.local.database.RoutineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoutineDatabase(
        @ApplicationContext context: Context
    ): RoutineDatabase {
        return Room.databaseBuilder(
            context = context,
            RoutineDatabase::class.java,
            "routine_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRoutineDao(routineDatabase: RoutineDatabase): RoutineDao {
        return routineDatabase.routineDao()
    }

}