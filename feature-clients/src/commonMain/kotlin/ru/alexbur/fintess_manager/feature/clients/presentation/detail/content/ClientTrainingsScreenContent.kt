package ru.alexbur.fintess_manager.feature.clients.presentation.detail.content

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexbur.fintess_manager.common_presentation.components.BaseScreen
import ru.alexbur.fintess_manager.feature.clients.presentation.list.AvatarGradient
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingItem
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingsAction
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingsViewState
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.TrainingDay
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.TrainingStatus
import ru.alexbur.fintess_manager.uikit.AppColors

// Локальная копия градиента выбранного дня — тот же визуальный контракт, что
// feature-calendar/.../ScreenTitle.kt (DayCell), сознательно не извлечена в uikit (см. plan.md
// Decisions Made — третье дублирование принято, чтобы не трогать feature-calendar).
private val SelectedDayGradient = Brush.horizontalGradient(
    colors = listOf(AppColors.Purple, AppColors.PurpleEnd),
)

private val RemainingWorkoutsGradient = Brush.linearGradient(
    colors = listOf(AppColors.GradientRedOrangeStart, AppColors.GradientRedOrangeEnd),
)

// Тёмный scrim поверх градиента карточки "ОСТАЛОСЬ ТРЕНИРОВОК", под текстом — обязателен, не
// опционален: расчёт WCAG-контраста на сырых токенах градиента не проходит AA даже с непрозрачным
// белым текстом (см. plan.md § UI / Decisions Made / Risks).
private val RemainingWorkoutsScrim = Brush.verticalGradient(
    colors = listOf(Color.Black.copy(alpha = 0.35f), Color.Black.copy(alpha = 0.15f)),
)

@Composable
internal fun ClientTrainingsScreenContent(
    state: ClientTrainingsViewState,
    onAction: (ClientTrainingsAction) -> Unit,
    onBackClick: () -> Unit,
) {
    // BaseScreen переиспользуется только как Box-контейнер (isLoading/toolbarState не передаются —
    // обе загрузки локализованы в своих секциях, см. plan.md § UI). Material TopAppBar не
    // используется — проект хендроллит header-composables, свой тёмный header ниже.
    BaseScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BgScreen),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ClientTrainingsHeader(onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(24.dp))
            ClientHeaderRow(state = state)
            Spacer(modifier = Modifier.height(20.dp))
            if (state.isDetailError) {
                DetailErrorBlock(onRetry = { onAction(ClientTrainingsAction.Retry) })
            } else {
                RemainingWorkoutsCard(state = state)
                Spacer(modifier = Modifier.height(16.dp))
                StatsRow(state = state)
            }
            Spacer(modifier = Modifier.height(24.dp))
            WeekDaySelector(
                days = state.days,
                selectedDayNumber = state.selectedDayNumber,
                onDaySelected = { dayNumber -> onAction(ClientTrainingsAction.DaySelected(dayNumber)) },
            )
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = state.sectionTitle)
            Spacer(modifier = Modifier.height(16.dp))
            TrainingsSection(
                state = state,
                onRetryTrainings = { onAction(ClientTrainingsAction.RetryTrainings) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ClientTrainingsHeader(onBackClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                // 48dp — минимальный рекомендованный touch target (не 40dp), визуальный круг может
                // быть меньше, см. plan.md § UI.
                .size(48.dp)
                .clip(CircleShape)
                .background(AppColors.BgDayCell)
                .clickable(role = Role.Button, onClick = onBackClick)
                .semantics { contentDescription = "Назад" },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "<",
                color = AppColors.TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Тренировки клиента",
            color = AppColors.TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ClientHeaderRow(state: ClientTrainingsViewState) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ClientDetailAvatar(initials = state.initials, gradient = state.avatarGradient)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = state.clientName,
                color = AppColors.TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            // Фиксированный слот высоты под "Клиент — с {дата}" — рендерится только когда
            // clientSinceText непусто (иначе обрубленный текст "Клиент — с " на loading/error), но
            // слот зарезервирован заранее, чтобы приход ответа не сдвигал остальной layout
            // (plan.md § UI, non-blocking polish note).
            Box(
                modifier = Modifier.height(18.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (state.clientSinceText.isNotBlank()) {
                    Text(
                        text = "Клиент — с ${state.clientSinceText}",
                        color = AppColors.TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun ClientDetailAvatar(initials: String, gradient: AvatarGradient) {
    val brush = when (gradient) {
        AvatarGradient.Purple -> Brush.linearGradient(
            colors = listOf(Color(0xFF6C63FF), Color(0xFF8B5CF6)),
        )
        AvatarGradient.RedOrange -> Brush.linearGradient(
            colors = listOf(Color(0xFFFF5C7A), Color(0xFFFF8C42)),
        )
        AvatarGradient.GreenBlue -> Brush.linearGradient(
            colors = listOf(Color(0xFF22D3A5), Color(0xFF4F8EF7)),
        )
        AvatarGradient.PurplePink -> Brush.linearGradient(
            colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
        )
    }
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(brush = brush),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun DetailErrorBlock(onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.BgCard)
            .border(width = 1.dp, color = AppColors.BgCardBorder, shape = RoundedCornerShape(20.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Не удалось загрузить профиль",
                color = AppColors.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) {
                Text(text = "Повторить")
            }
        }
    }
}

@Composable
private fun RemainingWorkoutsCard(state: ClientTrainingsViewState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(brush = RemainingWorkoutsGradient),
    ) {
        // Scrim-слой между фоновым градиентом и текстом — см. RemainingWorkoutsScrim.
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = RemainingWorkoutsScrim),
        )
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "ОСТАЛОСЬ ТРЕНИРОВОК",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.remainingWorkouts.toString(),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
            )
            if (state.workoutsStatusText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.workoutsStatusText,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
private fun StatsRow(state: ClientTrainingsViewState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatCard(label = "Вес", value = "${state.weightText} кг", modifier = Modifier.weight(1f))
        StatCard(label = "Рост", value = "${state.heightText} см", modifier = Modifier.weight(1f))
        StatCard(label = "Возраст", value = state.ageText, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(AppColors.BgDayCell)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = AppColors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = AppColors.TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Composable
private fun WeekDaySelector(
    days: List<TrainingDay>,
    selectedDayNumber: Int,
    onDaySelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEach { day ->
            TrainingDayCell(
                day = day,
                isSelected = day.number == selectedDayNumber,
                onClick = { onDaySelected(day.number) },
            )
        }
    }
}

@Composable
private fun TrainingDayCell(day: TrainingDay, isSelected: Boolean, onClick: () -> Unit) {
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
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = "${day.name}, ${day.number}, выбрать день" },
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
private fun TrainingsSection(
    state: ClientTrainingsViewState,
    onRetryTrainings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Три взаимоисключающих состояния — явно, не как решение на месте (см. plan.md § UI).
    when {
        state.isTrainingsLoading -> {
            Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
                CircularProgressIndicator(modifier = Modifier.size(28.dp))
            }
        }

        state.isTrainingsError -> {
            Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Не удалось загрузить тренировки",
                        color = AppColors.TextPrimary,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = onRetryTrainings) {
                        Text(text = "Повторить")
                    }
                }
            }
        }

        state.trainings.isEmpty() -> {
            Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
                Text(
                    text = "Тренировок нет",
                    color = AppColors.TextSecondary,
                    fontSize = 14.sp,
                )
            }
        }

        else -> {
            Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                state.trainings.forEach { training ->
                    TrainingCard(training = training)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun TrainingCard(training: ClientTrainingItem) {
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
                    text = training.timeRange,
                    color = AppColors.TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = training.subtitle,
                    color = AppColors.TextSecondary,
                    fontSize = 12.sp,
                )
            }
            TrainingStatusPill(status = training.status)
        }
    }
}

@Composable
private fun TrainingStatusPill(status: TrainingStatus) {
    val pillColor = when (status) {
        TrainingStatus.Active -> AppColors.Green
        TrainingStatus.Soon -> AppColors.Purple
    }
    val pillText = when (status) {
        TrainingStatus.Active -> "Активно"
        TrainingStatus.Soon -> "Скоро"
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
