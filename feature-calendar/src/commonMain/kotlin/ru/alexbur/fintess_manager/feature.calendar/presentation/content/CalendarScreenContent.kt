package ru.alexbur.fintess_manager.feature.calendar.presentation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.alexbur.fintess_manager.common_presentation.components.BaseScreen
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarAction
import ru.alexbur.fintess_manager.feature.calendar.presentation.CalendarViewState
import ru.alexbur.fintess_manager.uikit.AppColors

@Composable
internal fun CalendarScreenContent(
    state: CalendarViewState,
    onAction: (CalendarAction) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
) {
    BaseScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgScreen),
        isLoading = state.isLoading,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            ScreenTitle()
            Spacer(modifier = Modifier.height(8.dp))
            TrainerBadge()
            Spacer(modifier = Modifier.height(24.dp))
            WeekNavigation(
                days = state.days,
                selectedDayNumber = state.selectedDayNumber,
                onDaySelected = { dayNumber -> onAction(CalendarAction.DaySelected(dayNumber)) },
                onPreviousWeek = onPreviousWeek,
                onNextWeek = onNextWeek,
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