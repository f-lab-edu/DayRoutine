package com.hyeon.dayroutine.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "routine_completion",
    primaryKeys = ["routineId", "date"],
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("routineId")]
)
data class RoutineCompletionEntity(
    val routineId: Int,
    val isComplete: Boolean,
    val date: String
)
