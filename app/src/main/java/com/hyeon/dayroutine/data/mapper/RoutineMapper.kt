package com.hyeon.dayroutine.data.mapper

import com.hyeon.dayroutine.data.local.model.RoutineCompletionEntity
import com.hyeon.dayroutine.data.local.model.RoutineEntity
import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.domain.model.RoutineCompletion
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
        durationTime = LocalTime.parse(durationTime),
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
        startTime = startTime.toString(),
        durationTime = durationTime.toString(),
        repeatDays = repeatDays.joinToString(separator = ","),
        createdAt = createdAt.toString()
    )
}

fun RoutineCompletionEntity.toDomain(): RoutineCompletion {
    return RoutineCompletion(
        routineId = routineId,
        date = DayOfWeek.valueOf(date),
        isComplete = isComplete
    )
}

fun RoutineCompletion.toEntity(): RoutineCompletionEntity {
    return RoutineCompletionEntity(
        routineId = routineId,
        date = date.toString(),
        isComplete = isComplete
    )
}