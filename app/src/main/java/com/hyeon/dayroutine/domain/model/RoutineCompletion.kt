package com.hyeon.dayroutine.domain.model

import java.time.DayOfWeek

data class RoutineCompletion(
    val routineId: Int,
    val date: DayOfWeek,
    val isComplete: Boolean
)