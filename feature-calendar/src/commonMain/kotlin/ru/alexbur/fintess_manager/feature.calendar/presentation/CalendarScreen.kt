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

// ── Local color palette ────────────────────────────────────────────────────

private val BgScreen = Color(0xFF0F0F14)
private val BgDayCell = Color(0xFF1E1E2A)
private val BgCard = Color(0xFF1A1A26)
private val BgCardBorder = Color(0xFF2A2A3A)
private val BgNavBar = Color(0xFF16161E)
private val TextPrimary = Color(0xFFF0F0F8)
private val TextSecondary = Color(0xFF9898B8)
private val TextMuted = Color(0xFF5A5A78)
private val ColorPurple = Color(0xFF6C63FF)
private val ColorPurpleEnd = Color(0xFF8B5CF6)
private val ColorGreen = Color(0xFF22D3A5)

private val SelectedDayGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF6C63FF), Color(0xFF8B5CF6)),
)

private val FabGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF6C63FF), Color(0xFF8B5CF6)),
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
        backgroundColor = BgScreen,
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
        color = TextPrimary,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun TrainerBadge() {
    Box(
        modifier = Modifier
            .background(
                color = ColorPurple.copy(alpha = 0.13f),
                shape = RoundedCornerShape(9.dp),
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(
            text = "Тренер",
            color = ColorPurple,
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
                    Modifier.background(color = BgDayCell)
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
                color = if (isSelected) Color.White else TextMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = day.number.toString(),
                color = if (isSelected) Color.White else TextPrimary,
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
        color = TextPrimary,
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
            .background(color = BgCard)
            .border(width = 1.dp, color = BgCardBorder, shape = shape)
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
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${workout.title} · ${workout.trainer}",
                    color = TextSecondary,
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
        WorkoutStatus.Active -> ColorGreen
        WorkoutStatus.Soon -> ColorPurple
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
            .background(color = BgNavBar),
    ) {
        Divider(color = BgCardBorder, thickness = 1.dp)
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
                    color = if (isActive) ColorPurple.copy(alpha = 0.15f) else BgDayCell,
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (isActive) ColorPurple else TextMuted,
            fontSize = 9.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
