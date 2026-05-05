package com.hyeon.dayroutine.data.repository

import com.hyeon.dayroutine.data.datasource.RoutineDataSource
import com.hyeon.dayroutine.data.mapper.toDomain
import com.hyeon.dayroutine.data.mapper.toEntity
import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.domain.model.RoutineCompletion
import com.hyeon.dayroutine.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDataSource: RoutineDataSource
): RoutineRepository {
    override fun getAll(): Flow<List<Routine>> {
        return routineDataSource.getAll().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun insert(routine: Routine) {
        val routineEntity = routine.toEntity()

        routineDataSource.insert(routine = routineEntity)
    }

    override fun getAllCompletion(): Flow<List<RoutineCompletion>> {
        return routineDataSource.getAllCompletion().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun getCompletionsByDate(date: String): Flow<List<RoutineCompletion>> {
        return routineDataSource.getCompletionsByDate(date).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun updateCompletion(routineCompletion: RoutineCompletion) {
        val routineCompletionEntity = routineCompletion.toEntity()

        routineDataSource.updateCompletion(routineComplete = routineCompletionEntity)
    }
}