package com.hyeon.dayroutine.data.datasource

import androidx.room.Insert
import androidx.room.Query
import com.hyeon.dayroutine.data.local.model.RoutineCompletionEntity
import com.hyeon.dayroutine.data.local.model.RoutineEntity
import kotlinx.coroutines.flow.Flow

interface RoutineDataSource {
    // RoutineDao
    fun getAll(): Flow<List<RoutineEntity>>
    suspend fun insert(routine: RoutineEntity)

    // RoutineCompletionDao
    fun getAllCompletion(): Flow<List<RoutineCompletionEntity>>
    fun getCompletionsByDate(date: String): Flow<List<RoutineCompletionEntity>>
    suspend fun updateCompletion(routineComplete: RoutineCompletionEntity)
}
