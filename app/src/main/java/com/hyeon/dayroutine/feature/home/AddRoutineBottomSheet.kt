package com.hyeon.dayroutine.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale
import kotlin.collections.listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineBottomSheet(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var durationTime by remember { mutableStateOf("") }
    var selectedDates by remember { mutableStateOf(setOf<DayOfWeek>()) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color(0xFF1a1a2e)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "새 루틴 추가",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            RoutineNameEditBox(
                text = text,
                onValueChanged = { text = it }
            )

            RoutineIconSelector(
                selectedIcon = selectedIcon,
                onSelected = { selectedIcon = it }
            )

            RoutineTimeBox(
                startTime = startTime,
                durationTime = durationTime,
                onStartTimeValueChanged = { startTime = it },
                onDurationTimeValueChanged = { durationTime = it }
            )

            RoutineDateSelector(
                selectedDates = selectedDates,
                onSelected = { date ->
                    selectedDates = if (selectedDates.contains(date)) selectedDates - date else selectedDates + date
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6c63ff)
                ),
                onClick = { /** 루틴 저장하기 **/ }
            ) {
                Text(
                    text = "루틴 저장하기",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun RoutineNameEditBox(
    text: String,
    onValueChanged: (String) -> Unit
) {
    Text(
        text = "루틴 이름",
        color = Color.Gray,
        fontSize = 12.sp,
    )
    /** Basic TextField로 디테일한 TextField 수정 **/
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = "예) 스트레칭, 독서...",
                color = Color(0xFF555577)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
    )
}

@Composable
fun RoutineIconSelector(
    selectedIcon: String,
    onSelected: (String) -> Unit
) {
    Text(
        text = "아이콘",
        color = Color.Gray,
        fontSize = 12.sp,
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val itemMinWidth = 50.dp
        val space = 8.dp

        /** 전체너비 = 아이템너비 * n + spacing * (n-1)
         * 전체너비 = 아이템너비 * n + spacing * n - spacing
         * 전체너비 + spacing = n(아이템너비 + spacing)
         *
         * 아이템너비 * n = 전체너비 - spacing * (n-1)
         * 아이템 너비 = (전체너비 + spacing - (spacing * n)) / n
         * **/
        val maxItems = ((maxWidth + space) / (itemMinWidth + space)).toInt()
        val itemWidth = (maxWidth - space * (maxItems - 1)) / maxItems

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            routineIconList.forEach { icon ->
                Surface(
                    modifier = Modifier.size(itemWidth),
                    shape = RoundedCornerShape(10.dp),
                    color = if (icon == selectedIcon) Color(0xFF6c63ff) else Color((0xFF1a1a2e)),
                    border = if (icon == selectedIcon) BorderStroke(
                        width = 1.5.dp,
                        color = Color(0xFF6c63ff)
                    )
                    else BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF2a2a3e)
                    ),
                    onClick = { onSelected(icon) }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = icon,
                        )
                    }
                }
            }
        }
    }

}

private val routineIconList = listOf("🧘", "🏃", "📖", "✍️", "💊", "🥗", "💧", "🎯")

@Composable
fun RoutineTimeBox(
    startTime: String,
    durationTime: String,
    onStartTimeValueChanged: (String) -> Unit,
    onDurationTimeValueChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimeEditBox(
            modifier = Modifier.weight(1f),
            timeLabel = "시작 시간",
            timeText = startTime,
            onValueChanged = onStartTimeValueChanged,
            placeHolder = {
                Text(
                    text = "오전 7:00",
                    fontSize = 14.sp,
                    color = Color(0xFF555577)
                )
            }
        )

        TimeEditBox(
            modifier = Modifier.weight(1f),
            timeLabel = "소요 시간",
            timeText = durationTime,
            onValueChanged = onDurationTimeValueChanged,
            placeHolder = {
                Text(
                    text = "10분",
                    fontSize = 14.sp,
                    color = Color(0xFF555577)
                )
            }
        )
    }
}

@Composable
fun TimeEditBox(
    modifier: Modifier = Modifier,
    timeLabel: String,
    timeText: String,
    onValueChanged: (String) -> Unit,
    placeHolder: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = timeLabel,
            color = Color.Gray,
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = timeText,
            onValueChange = onValueChanged,
            placeholder = placeHolder,
            singleLine = true,
            shape = RoundedCornerShape(20.dp)
        )

    }
}

@Composable
fun RoutineDateSelector(
    selectedDates: Set<DayOfWeek>,
    onSelected: (DayOfWeek) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "반복 요일",
            color = Color.Gray,
            fontSize = 12.sp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DayOfWeek.entries.forEach { date ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(
                            color = if (selectedDates.contains(date)) Color(0xFF6c63ff) else Color(0xFF1a1a2e),
                            shape = CircleShape
                        )
                        .border(
                            if (selectedDates.contains(date)) BorderStroke(width = 1.dp, color = Color(0xFF6c63ff))
                            else BorderStroke(width = 1.5.dp, color = Color(0xFF2a2a3e)),
                            shape = CircleShape
                        )
                        .clickable { onSelected(date) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = date.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                        color = if (selectedDates.contains(date)) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}