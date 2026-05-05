package com.hyeon.dayroutine.feature.home

import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.feature.home.model.RoutineWithCheck
import java.time.LocalDate

class HomeContract {
    data class HomeState(
        val routineList: List<RoutineWithCheck> = emptyList(),
        val selectedDate: LocalDate = LocalDate.now(),
        val isShowAddRoutineBottomSheet: Boolean = false
    )
    sealed interface HomeIntent {
        data class ClickRoutine(val routine: Routine): HomeIntent
        data class CheckRoutine(val routine: Routine, val isChecked: Boolean): HomeIntent
        data class SelectDate(val date: LocalDate): HomeIntent
        data object ShowAddBottomSheet: HomeIntent
        data object HideAddBottomSheet: HomeIntent
    }
    sealed interface HomeSideEffect {
        data class NavigateToDetail(val routine: Routine): HomeSideEffect
    }
}