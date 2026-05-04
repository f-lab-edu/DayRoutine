package com.hyeon.dayroutine.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class Routine(
    val id: Int,
    val title: String,
    val icon: String,
    val startTime: LocalTime,
    val durationTime: LocalTime,
    val repeatDays: List<DayOfWeek>,
    val createdAt: LocalDate
)
