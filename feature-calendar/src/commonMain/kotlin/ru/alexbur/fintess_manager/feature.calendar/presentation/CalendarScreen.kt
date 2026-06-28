package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.uikit.AppColors

private val SelectedDayGradient = Brush.horizontalGradient(
    colors = listOf(AppColors.Purple, AppColors.PurpleEnd),
)

private val FabGradient = Brush.horizontalGradient(
    colors = listOf(AppColors.Purple, AppColors.PurpleEnd),
)

private val BottomNavLabels = listOf("Расписание", "Клиенты", "Тренировки", "Профиль")

// ── Entry point (navigator-aware) ─────────────────────────────────────────

@Composable
internal fun CalendarScreen(
    navigator: Navigator,
    viewModel: CalendarViewModel = koinViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    CalendarScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                is CalendarAction.BackClicked -> navigator.popBackStack()
                is CalendarAction.AddClicked,
                is CalendarAction.DaySelected -> viewModel.obtainAction(action)
            }
        },
    )
}

// ── Stateless screen ───────────────────────────────────────────────────────

@Composable
private fun CalendarScreenContent(
    state: CalendarViewState,
    onAction: (CalendarAction) -> Unit,
) {
    Scaffold(
        backgroundColor = AppColors.BgScreen,
        floatingActionButton = {
            AddFab(onClick = { onAction(CalendarAction.AddClicked()) })
        },
        bottomBar = { CalendarBottomNavBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            ScreenTitle()
            Spacer(modifier = Modifier.height(8.dp))
            TrainerBadge()
            Spacer(modifier = Modifier.height(24.dp))
            WeekStrip(
                days = state.days,
                selectedDayNumber = state.selectedDayNumber,
                onDaySelected = { dayNumber -> onAction(CalendarAction.DaySelected(dayNumber)) },
            )
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = state.sectionTitle)
            Spacer(modifier = Modifier.height(16.dp))
            state.workouts.forEach { workout ->
                WorkoutCard(workout = workout)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

// ── Sub-composables ────────────────────────────────────────────────────────

@Composable
private fun ScreenTitle() {
    Text(
        text = "Мои тренировки",
        color = AppColors.TextPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun TrainerBadge() {
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
private fun WeekStrip(
    days: List<CalendarDay>,
    selectedDayNumber: Int,
    onDaySelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
private fun SectionHeader(title: String) {
    Text(
        text = title,
        color = AppColors.TextPrimary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun WorkoutCard(workout: WorkoutItem) {
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

@Composable
private fun AddFab(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(brush = FabGradient)
            .clickable(
                role = Role.Button,
                onClickLabel = "Добавить тренировку",
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "+",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun CalendarBottomNavBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppColors.BgNavBar),
    ) {
        Divider(color = AppColors.BgCardBorder, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BottomNavLabels.forEachIndexed { index, label ->
                BottomNavItem(
                    label = label,
                    isActive = index == 0,
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(label: String, isActive: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            role = Role.Tab,
            // no-op: bottom nav items are display-only in this mock
            onClick = {},
        ),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = if (isActive) AppColors.PurpleTabActive else AppColors.BgDayCell,
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (isActive) AppColors.Purple else AppColors.TextMuted,
            fontSize = 9.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
