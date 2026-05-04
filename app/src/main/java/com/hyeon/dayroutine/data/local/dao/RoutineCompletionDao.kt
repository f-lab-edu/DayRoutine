package com.hyeon.dayroutine.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.hyeon.dayroutine.data.local.model.RoutineCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineCompletionDao {
    /** SQL문 작성 **/
    @Query("SELECT * FROM routine_completion")
    fun getAllCompletion(): Flow<List<RoutineCompletionEntity>>

    @Query("SELECT * FROM routine_completion WHERE date = :date")
    fun getCompletionsByDate(date: String): Flow<List<RoutineCompletionEntity>>

    @Upsert
    suspend fun updateCompletion(routineComplete: RoutineCompletionEntity)
}