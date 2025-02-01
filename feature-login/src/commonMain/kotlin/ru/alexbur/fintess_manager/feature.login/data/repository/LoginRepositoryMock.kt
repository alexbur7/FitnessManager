package ru.alexbur.fintess_manager.feature.login.data.repository

import kotlinx.coroutines.delay
import ru.alexbur.fintess_manager.feature.login.domain.models.GetOtp
import ru.alexbur.fintess_manager.feature.login.domain.models.Login
import ru.alexbur.fintess_manager.feature.login.domain.repository.LoginRepository

internal class LoginRepositoryMock : LoginRepository {

    override suspend fun getOtp(phoneNumber: String): GetOtp {
        delay(200L)
        return GetOtp(userId = 1)
    }

    override suspend fun login(
        userId: Long,
        otp: String
    ): Login {
        delay(200L)
        return Login(token = "fknwkpqfnqwpif")
    }
}