package com.hyeon.dayroutine.data.mapper

import com.hyeon.dayroutine.data.local.model.RoutineEntity
import com.hyeon.dayroutine.domain.model.Routine
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun RoutineEntity.toDomain():Routine {
    return Routine(
        id = id,
        title = title,
        icon = icon,
        startTime = LocalTime.parse(startTime),
        repeatDays = repeatDays.split(",")
            .map { day -> DayOfWeek.valueOf(day) },
        createdAt = LocalDate.parse(createdAt)
    )
}

fun Routine.toEntity(): RoutineEntity {
    return RoutineEntity(
        id = id,
        title = title,
        icon = icon,
        startTime = startTime.format(DateTimeFormatter.ofPattern("hh:mm")),
        repeatDays = repeatDays.joinToString { "," },
        createdAt = createdAt.format(DateTimeFormatter.ofPattern("mm-dd"))
    )
}