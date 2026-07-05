package ru.alexbur.fintess_manager.feature.clients.presentation.detail

import kotlinx.datetime.LocalDate
import kotlin.math.abs
import kotlin.math.roundToInt

// "января", "февраля", ... — индексируется LocalDate.monthNumber - 1 (1-based -> 0-based).
private val monthGenitiveNames = listOf(
    "января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря",
)

// String.format недоступен в commonMain KMP (нет JVM actual для iOS target) — ручное округление
// до одного знака после запятой без locale-зависимой запятой/точки.
internal fun formatWeight(kg: Double): String {
    val tenths = (kg * 10).roundToInt()
    return "${tenths / 10}.${abs(tenths % 10)}"
}

internal fun formatClientSince(date: LocalDate): String =
    "${date.dayOfMonth} ${monthGenitiveNames[date.monthNumber - 1]} ${date.year}"

internal fun buildSectionTitle(count: Int, isToday: Boolean, dayName: String): String {
    val prefix = if (isToday) "Сегодня" else dayName
    val word = when {
        count % 100 in 11..14 -> "тренировок"
        count % 10 == 1 -> "тренировка"
        count % 10 in 2..4 -> "тренировки"
        else -> "тренировок"
    }
    return "$prefix — $count $word"
}

internal fun computeInitials(name: String): String =
    name.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")
