package com.hyeon.dayroutine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hyeon.dayroutine.data.local.model.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM routine")
    fun getAll(): Flow<List<RoutineEntity>>

    @Insert
    suspend fun insert(routine: RoutineEntity)
}