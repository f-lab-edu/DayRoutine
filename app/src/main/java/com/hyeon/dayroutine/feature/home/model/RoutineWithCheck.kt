package com.hyeon.dayroutine.feature.home.model

import com.hyeon.dayroutine.domain.model.Routine

data class RoutineWithCheck (
    val routine: Routine,
    val isChecked: Boolean
)