package com.hyeon.dayroutine.domain.repository

import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.domain.model.RoutineCompletion
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getAll(): Flow<List<Routine>>
    suspend fun insert(routine: Routine)

    fun getAllCompletion(): Flow<List<RoutineCompletion>>

    fun getCompletionsByDate(date: String): Flow<List<RoutineCompletion>>
    suspend fun updateCompletion(routineCompletion: RoutineCompletion)
}