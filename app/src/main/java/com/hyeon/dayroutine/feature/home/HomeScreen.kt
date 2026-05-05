package com.hyeon.dayroutine.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.feature.home.model.RoutineWithCheck
import com.hyeon.dayroutine.ui.component.RoutineProgress
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import com.hyeon.dayroutine.feature.home.HomeContract.*
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dayChipGroupListState = rememberLazyListState()
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF13131f),
        topBar = { TopBar(state) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {
            DayChipGroupList(
                dayChipGroupListState = dayChipGroupListState,
                selectedDate = state.selectedDate
            ) { viewModel.handleIntent(HomeIntent.SelectDate(it)) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "루틴 목록",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            isShowBottomSheet = true
                        },
                    text = "+ 추가",
                    color = Color(0xFF6c63ff),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            RoutineList(
                routineList = state.routineList,
                onClickRoutine = { /** 루틴 상세 페이지 이동 **/ },
                onChecked = { routine, isChecked ->
                    viewModel.handleIntent(HomeIntent.CheckRoutine(routine, isChecked))
                }
            )

            if (isShowBottomSheet) {
                AddRoutineBottomSheet(
                    bottomSheetState = bottomSheetState
                ) {
                    isShowBottomSheet = false
                }
            }
        }
    }
}

@Composable
fun TopBar(
    state: HomeState
) {
    val totalRoutineCnt = state.routineList.size
    val completeRoutineCnt = state.routineList.count { it.isChecked }
    val progress = if(totalRoutineCnt == 0) 0f else completeRoutineCnt.toFloat() / totalRoutineCnt

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1a1a2e))
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        Text(
            text = "오늘의 루틴을 향하여🔥",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "오늘의 루틴",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF252540),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "오늘의 진행률",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = "${completeRoutineCnt}/${totalRoutineCnt} 완료",
                    color = Color(0xFF8888aa),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            RoutineProgress(
                modifier = Modifier.fillMaxWidth(),
                progress = progress
            )
        }
    }
}

@Composable
fun DayChipGroupList(
    dayChipGroupListState: LazyListState,
    selectedDate: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    val weeks = remember {
        val monday = LocalDate.now().with(DayOfWeek.MONDAY)

        (0..6).map { monday.plusDays(it.toLong()) }
    }

    LazyRow(
        state = dayChipGroupListState,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(weeks) {
            DayOfWeekItem(
                date = it,
                isSelected = it == selectedDate,
                onDayClick = onDayClick
            )
        }
    }
}

@Composable
fun DayOfWeekItem(
    date: LocalDate,
    isSelected: Boolean,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .size(width = 45.dp, height = 60.dp)
            .background(
                if (isSelected) Color(0xFF6c63ff) else Color(0xFF1e1e30),
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onDayClick(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = date.dayOfWeek.getDisplayName(TextStyle.SHORT,Locale.ENGLISH),
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFFd4d0ff) else Color(0xFF555577)
        )

        Text(
            text = date.dayOfMonth.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color(0xFFffffff) else Color(0xFFcccccc)
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(shape = CircleShape)
                .background(color = if (isSelected) Color(0xFFa09fff) else Color(0xFF33334a))
        )
    }
}

@Composable
fun RoutineList(
    routineList: List<RoutineWithCheck>,
    onClickRoutine: (RoutineWithCheck) -> Unit,
    onChecked: (Routine, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(routineList) {
            RoutineItem(
                routineItem = it,
                onClickRoutine = onClickRoutine,
                onChecked = onChecked,
            )
        }
    }
}

@Composable
fun RoutineItem(
    routineItem: RoutineWithCheck,
    onClickRoutine: (RoutineWithCheck) -> Unit,
    onChecked: (Routine, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF1a1a2e), shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color(0xFF2a2a3e), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
            .clickable {
                onClickRoutine(routineItem)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(50.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
                .border(width = 1.dp, color = Color(0xFF2a2a3e), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = routineItem.routine.icon,
                fontSize = 25.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = routineItem.routine.title,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "오전 ${routineItem.routine.startTime.format(DateTimeFormatter.ofPattern("hh:mm"))}" +
                        " · ${routineItem.routine.durationTime.format(DateTimeFormatter.ofPattern("mm"))}분",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Checkbox(
            checked = routineItem.isChecked,
            onCheckedChange = { onChecked(routineItem.routine, routineItem.isChecked) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF6c63ff),
                checkmarkColor = Color.White
            )
        )
    }
}
