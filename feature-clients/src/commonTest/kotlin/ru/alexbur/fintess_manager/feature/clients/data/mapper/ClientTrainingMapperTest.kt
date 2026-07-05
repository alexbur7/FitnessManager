package ru.alexbur.fintess_manager.feature.clients.data.mapper

import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientTrainingResponse
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTrainingStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ClientTrainingMapperTest {

    private val mapper = ClientTrainingMapper()

    @Test
    fun `map copies fields and resolves known status`() {
        val dto = ClientTrainingResponse(
            id = 42L,
            startTime = "08:00",
            endTime = "09:00",
            durationMinutes = 60,
            title = "Силовая",
            status = "active",
        )

        val result = mapper.map(dto)

        assertEquals(42L, result.id)
        assertEquals("08:00", result.startTime)
        assertEquals("09:00", result.endTime)
        assertEquals(60, result.durationMinutes)
        assertEquals("Силовая", result.title)
        assertEquals(ClientTrainingStatus.ACTIVE, result.status)
    }

    @Test
    fun `map falls back to SOON for unknown status`() {
        val dto = ClientTrainingResponse(
            id = 1L,
            startTime = "10:00",
            endTime = "11:00",
            durationMinutes = 30,
            title = "Кардио",
            status = "bogus",
        )

        val result = mapper.map(dto)

        assertEquals(ClientTrainingStatus.SOON, result.status)
    }
}
