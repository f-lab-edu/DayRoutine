package com.hyeon.dayroutine.data.local.datasource

import com.hyeon.dayroutine.data.datasource.RoutineDataSource
import com.hyeon.dayroutine.data.local.dao.RoutineCompletionDao
import com.hyeon.dayroutine.data.local.dao.RoutineDao
import com.hyeon.dayroutine.data.local.model.RoutineCompletionEntity
import com.hyeon.dayroutine.data.local.model.RoutineEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineDataSourceImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val routineCompletionDao: RoutineCompletionDao
): RoutineDataSource {
    override fun getAll(): Flow<List<RoutineEntity>> {
        return routineDao.getAll()
    }

    override suspend fun insert(routine: RoutineEntity) {
        routineDao.insert(routine = routine)
    }

    override fun getAllCompletion(): Flow<List<RoutineCompletionEntity>> {
        return routineCompletionDao.getAllCompletion()
    }

    override fun getCompletionsByDate(date: String): Flow<List<RoutineCompletionEntity>> {
        return routineCompletionDao.getCompletionsByDate(date = date)
    }

    override suspend fun updateCompletion(routineComplete: RoutineCompletionEntity) {
        routineCompletionDao.updateCompletion(routineComplete = routineComplete)
    }
}