package com.hyeon.dayroutine.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.domain.model.RoutineCompletion
import com.hyeon.dayroutine.domain.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.hyeon.dayroutine.feature.home.HomeContract.*
import com.hyeon.dayroutine.feature.home.model.RoutineWithCheck
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routineRepository: RoutineRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                routineRepository.getAll(),
                _state.map { it.selectedDate }.flatMapLatest {
                    routineRepository.getCompletionsByDate(it.dayOfWeek.name)
                }
            ) { routines, routineCompletions ->
                routines.map { routine ->
                    RoutineWithCheck(
                        routine = routine,
                        isChecked = routineCompletions.any {
                            it.routineId == routine.id && it.isComplete
                        }
                    )
                }
            }.collect { routineWithChecks ->
                _state.update { it.copy(routineList = routineWithChecks) }
            }
        }
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.CheckRoutine -> checkRoutine(intent.routine, intent.isChecked)
            is HomeIntent.ClickRoutine -> clickRoutine(intent.routine)
            is HomeIntent.SelectDate -> selectDate(intent.date)
            HomeIntent.ShowAddBottomSheet -> showAddBottomSheet()
            HomeIntent.HideAddBottomSheet -> hideAddBottomSheet()
        }
    }

    private fun checkRoutine(routine: Routine, isChecked: Boolean) {
        val routineCompletion = RoutineCompletion(
            routineId = routine.id,
            date = _state.value.selectedDate.dayOfWeek,
            isComplete = !isChecked
        )

        viewModelScope.launch {
            routineRepository.updateCompletion(routineCompletion)
        }
    }

    private fun clickRoutine(routine: Routine) {
        /** Routine Detail Screen 이동 **/
    }

    private fun selectDate(date: LocalDate) {
        _state.update { it.copy(selectedDate = date) }
    }

    private fun showAddBottomSheet() {
        _state.update { it.copy(isShowAddRoutineBottomSheet = true) }
    }

    private fun hideAddBottomSheet() {
        _state.update { it.copy(isShowAddRoutineBottomSheet = true) }
    }
}