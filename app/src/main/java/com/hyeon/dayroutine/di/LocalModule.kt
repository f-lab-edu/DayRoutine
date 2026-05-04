package com.hyeon.dayroutine.di

import com.hyeon.dayroutine.data.datasource.RoutineDataSource
import com.hyeon.dayroutine.data.local.datasource.RoutineDataSourceImpl
import com.hyeon.dayroutine.data.repository.RoutineRepositoryImpl
import com.hyeon.dayroutine.domain.repository.RoutineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindRoutineDataSource(impl: RoutineDataSourceImpl): RoutineDataSource

    @Binds
    abstract fun bindRoutineRepository(impl: RoutineRepositoryImpl): RoutineRepository
}