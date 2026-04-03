package com.hyeon.dayroutine.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine")
data class RoutineEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val icon: String,
    val startTime: String,
    val repeatDays: String,
    val createdAt: String
)
