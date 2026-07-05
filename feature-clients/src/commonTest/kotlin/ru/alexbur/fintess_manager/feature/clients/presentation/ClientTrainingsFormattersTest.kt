package ru.alexbur.fintess_manager.feature.clients.presentation

import kotlinx.datetime.LocalDate
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTrainingStatus
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.buildSectionTitle
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.computeInitials
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.formatClientSince
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.formatWeight
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientTrainingsFormattersTest {

    @Test
    fun `formatWeight rounds to one decimal digit`() {
        assertEquals("68.4", formatWeight(68.44))
    }

    @Test
    fun `formatClientSince renders genitive month name`() {
        assertEquals("1 марта 2026", formatClientSince(LocalDate(2026, 3, 1)))
    }

    @Test
    fun `buildSectionTitle pluralizes single training`() {
        assertEquals("Сегодня — 1 тренировка",
            buildSectionTitle(count = 1, isToday = true, dayName = "Пн")
        )
    }

    @Test
    fun `buildSectionTitle pluralizes few trainings`() {
        assertEquals("Пн — 2 тренировки",
            buildSectionTitle(count = 2, isToday = false, dayName = "Пн")
        )
    }

    @Test
    fun `buildSectionTitle pluralizes many trainings`() {
        assertEquals("Пн — 5 тренировок",
            buildSectionTitle(count = 5, isToday = false, dayName = "Пн")
        )
    }

    @Test
    fun `computeInitials takes first two initials`() {
        assertEquals("ИИ", computeInitials("Иван Иванов"))
    }

    @Test
    fun `ClientTrainingStatus create falls back to SOON for unknown value`() {
        assertEquals(ClientTrainingStatus.SOON, ClientTrainingStatus.create("bogus"))
    }
}
