package com.hyeon.dayroutine.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyeon.dayroutine.data.local.dao.RoutineCompletionDao
import com.hyeon.dayroutine.data.local.dao.RoutineDao
import com.hyeon.dayroutine.data.local.model.RoutineCompletionEntity
import com.hyeon.dayroutine.data.local.model.RoutineEntity

@Database(
    entities = [RoutineEntity::class, RoutineCompletionEntity::class],
    version = 1
)
abstract class RoutineDatabase: RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun routineCompletionDao(): RoutineCompletionDao
}