package com.hyeon.dayroutine.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyeon.dayroutine.domain.model.Routine
import com.hyeon.dayroutine.ui.component.RoutineProgress
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val dayOfWeekList = testDayDate()
    val dayChipGroupListState = rememberLazyListState()
    var isShowBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var checkTest by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF13131f),
        topBar = { TopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {
            DayChipGroupList(
                dayChipGroupListState = dayChipGroupListState,
                dayOfWeekList = dayOfWeekList,
            ) { /** 해당 날짜 루틴 목록 업데이트 **/ }
 
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
                        .clickable{
                            isShowBottomSheet = true
                        },
                    text = "+ 추가",
                    color = Color(0xFF6c63ff),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            RoutineList(
                routineList = routines,
                onClickRoutine = { /** 루틴 상세 페이지 이동 **/ },
                isChecked = checkTest,
                onChecked = { checkTest = it }
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
fun TopBar() {
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
                    text = "3/5 완료",
                    color = Color(0xFF8888aa),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            RoutineProgress(
                modifier = Modifier.fillMaxWidth(),
                progress = 0.6f
            )
        }
    }
}

@Composable
fun DayChipGroupList(
    dayChipGroupListState: LazyListState,
    dayOfWeekList: List<DayItem>,
    onDayClick: (DayItem) -> Unit
) {
    BoxWithConstraints {
        val width = maxWidth
        val density = LocalDensity.current

        LazyRow(
            state = dayChipGroupListState,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dayOfWeekList) {
                DayOfWeekItem(
                    dayItem = it,
                    onDayClick = onDayClick
                )
            }
        }

        LaunchedEffect(Unit) {
            val offset = with(density) {
                ((width / 2) - (50 / 2).dp).toPx()
            }

            dayChipGroupListState.animateScrollToItem(
                index = 5,
                scrollOffset = -offset.toInt()
            )
        }
    }
}

@Composable
fun DayOfWeekItem(
    dayItem: DayItem,
    onDayClick: (DayItem) -> Unit
) {
    Column(
        modifier = Modifier
            .size(width = 50.dp, height = 60.dp)
            .background(
                if (dayItem.isToday) Color(0xFF6c63ff) else Color(0xFF1e1e30),
                shape = RoundedCornerShape(6.dp)
            )
            .clickable {
                onDayClick(dayItem)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = dayItem.dayOfWeek,
            fontSize = 12.sp,
            color = if (dayItem.isToday) Color(0xFFd4d0ff) else Color(0xFF555577)
        )

        Text(
            text = dayItem.day.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (dayItem.isToday) Color(0xFFffffff) else Color(0xFFcccccc)
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(shape = CircleShape)
                .background(color = if (dayItem.isToday) Color(0xFFa09fff) else Color(0xFF33334a))
        )
    }
}

@Composable
fun RoutineList(
    routineList: List<Routine>,
    onClickRoutine: (Routine) -> Unit,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(routineList) {
            RoutineItem(
                routineItem = it,
                onClickRoutine = onClickRoutine,
                isChecked = isChecked,
                onChecked = onChecked,
            )
        }
    }
}

@Composable
fun RoutineItem(
    routineItem: Routine,
    onClickRoutine: (Routine) -> Unit,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
       modifier = Modifier
           .fillMaxWidth()
           .background(color = Color(0xFF1a1a2e), shape = RoundedCornerShape(12.dp))
           .border(width = 1.dp, color = Color(0xFF2a2a3e), shape = RoundedCornerShape(12.dp))
           .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: 추후 이미지사용
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(50.dp)
                .background(color = Color.Red, shape = RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .clickable{
                    onClickRoutine(routineItem)
                },
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "운동명",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "시작시간 및 소요시간",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Checkbox(
            checked = isChecked,
            onCheckedChange = { onChecked(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF6c63ff),
                checkmarkColor = Color.White
            )
        )

    }

}

fun testDayDate(): List<DayItem> {
    val today = LocalDate.now()
    val range = -5..5

    return range.map {
        val day = today.plusDays(it.toLong())

        DayItem(
            dayOfWeek = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
            day = day.dayOfMonth,
            isToday = it == 0
        )
    }
}

val routines = listOf(
    Routine(
        id = 1,
        title = "스트레칭",
        icon = "🧘",
        startTime = LocalTime.of(7, 0),
        repeatDays = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        ),
        createdAt = LocalDate.of(2025, 3, 1)
    ),
    Routine(
        id = 2,
        title = "독서",
        icon = "📖",
        startTime = LocalTime.of(7, 30),
        repeatDays = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.FRIDAY
        ),
        createdAt = LocalDate.of(2025, 3, 1)
    ),
    Routine(
        id = 3,
        title = "모닝 커피",
        icon = "☕",
        startTime = LocalTime.of(8, 0),
        repeatDays = DayOfWeek.entries.toList(),  // 매일
        createdAt = LocalDate.of(2025, 3, 5)
    ),
    Routine(
        id = 4,
        title = "조깅",
        icon = "🏃",
        startTime = LocalTime.of(8, 30),
        repeatDays = listOf(
            DayOfWeek.TUESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.SATURDAY
        ),
        createdAt = LocalDate.of(2025, 3, 10)
    ),
    Routine(
        id = 5,
        title = "명상",
        icon = "🧘",
        startTime = LocalTime.of(9, 0),
        repeatDays = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SUNDAY
        ),
        createdAt = LocalDate.of(2025, 3, 15)
    ),
    Routine(
        id = 6,
        title = "일기 쓰기",
        icon = "✍️",
        startTime = LocalTime.of(22, 0),
        repeatDays = DayOfWeek.entries.toList(),  // 매일
        createdAt = LocalDate.of(2025, 3, 20)
    )
)

data class DayItem(
    val dayOfWeek: String,
    val day: Int,
    val isToday: Boolean,
)
