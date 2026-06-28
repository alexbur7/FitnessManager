package ru.alexbur.fintess_manager.feature.calendar.presentation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarDay
import ru.alexbur.fintess_manager.feature.calendar.presentation.WorkoutItem
import ru.alexbur.fintess_manager.feature.calendar.presentation.WorkoutStatus
import ru.alexbur.fintess_manager.uikit.AppColors

private val SelectedDayGradient = Brush.horizontalGradient(
    colors = listOf(AppColors.Purple, AppColors.PurpleEnd),
)

@Composable
internal fun ScreenTitle() {
    Text(
        text = "Мои тренировки",
        color = AppColors.TextPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
internal fun TrainerBadge() {
    Box(
        modifier = Modifier
            .background(
                color = AppColors.Purple.copy(alpha = 0.13f),
                shape = RoundedCornerShape(9.dp),
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text = "Тренер",
            color = AppColors.Purple,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
internal fun WeekNavigation(
    days: List<CalendarDay>,
    selectedDayNumber: Int,
    onDaySelected: (Int) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onPreviousWeek) {
            Text(
                text = "←",
                color = AppColors.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        WeekStrip(
            days = days,
            selectedDayNumber = selectedDayNumber,
            onDaySelected = onDaySelected,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onNextWeek) {
            Text(
                text = "→",
                color = AppColors.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun WeekStrip(
    days: List<CalendarDay>,
    selectedDayNumber: Int,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEach { day ->
            DayCell(
                day = day,
                isSelected = day.number == selectedDayNumber,
                onClick = { onDaySelected(day.number) },
            )
        }
    }
}

@Composable
private fun DayCell(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(14.dp)
    Box(
        modifier = Modifier
            .width(43.dp)
            .height(62.dp)
            .clip(shape)
            .then(
                if (isSelected) {
                    Modifier.background(brush = SelectedDayGradient)
                } else {
                    Modifier.background(color = AppColors.BgDayCell)
                },
            )
            .clickable(
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = day.name,
                color = if (isSelected) Color.White else AppColors.TextMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = day.number.toString(),
                color = if (isSelected) Color.White else AppColors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
internal fun SectionHeader(title: String) {
    Text(
        text = title,
        color = AppColors.TextPrimary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
internal fun WorkoutCard(workout: WorkoutItem) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(shape)
            .background(color = AppColors.BgCard)
            .border(width = 1.dp, color = AppColors.BgCardBorder, shape = shape)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "${workout.time} — ${workout.duration}",
                    color = AppColors.TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${workout.title} · ${workout.trainer}",
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                )
            }
            WorkoutStatusPill(status = workout.status)
        }
    }
}

@Composable
private fun WorkoutStatusPill(status: WorkoutStatus) {
    val pillColor = when (status) {
        WorkoutStatus.Active -> AppColors.Green
        WorkoutStatus.Soon -> AppColors.Purple
    }
    val pillText = when (status) {
        WorkoutStatus.Active -> "Активно"
        WorkoutStatus.Soon -> "Скоро"
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = pillColor)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = pillText,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}