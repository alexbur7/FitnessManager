package ru.alexbur.fintess_manager.feature.clients.data.mapper

import kotlinx.datetime.LocalDate
import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientDetailResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientDetailMapperTest {

    private val mapper = ClientDetailMapper()

    @Test
    fun `map parses clientSince and copies all fields`() {
        val dto = ClientDetailResponse(
            relationshipsId = "rel-1",
            firstName = "Иван",
            lastName = "Иванов",
            remainingWorkouts = 5,
            workoutsStatus = "Пора предложить продление!",
            weightKg = 68.4,
            heightCm = 178,
            age = 29,
            clientSince = "2026-03-01",
        )

        val result = mapper.map(dto)

        assertEquals("rel-1", result.relationshipsId)
        assertEquals("Иван Иванов", result.name)
        assertEquals(5, result.remainingWorkouts)
        assertEquals("Пора предложить продление!", result.workoutsStatus)
        assertEquals(68.4, result.weightKg)
        assertEquals(178, result.heightCm)
        assertEquals(29, result.age)
        assertEquals(LocalDate(2026, 3, 1), result.clientSince)
    }

    @Test
    fun `map handles null workoutsStatus`() {
        val dto = ClientDetailResponse(
            relationshipsId = "rel-2",
            firstName = "Пётр",
            lastName = "Петров",
            remainingWorkouts = 0,
            workoutsStatus = null,
            weightKg = 80.0,
            heightCm = 182,
            age = 40,
            clientSince = "2025-11-20",
        )

        val result = mapper.map(dto)

        assertEquals(null, result.workoutsStatus)
        assertEquals(LocalDate(2025, 11, 20), result.clientSince)
    }
}
