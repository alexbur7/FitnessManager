package ru.alexbur.fintess_manager.feature.login.domain.interactor

import ru.alexbur.fintess_manager.core.utils.safeRunCatching
import ru.alexbur.fintess_manager.feature.login.domain.repository.LoginRepository

internal class LoginInteractor(
    private val repository: LoginRepository,
) {
    suspend fun getOtp(phoneNumber: String) = safeRunCatching {
        repository.getOtp(phoneNumber)
    }

    suspend fun login(userId: Long, otp: String) = safeRunCatching {
        repository.login(userId, otp)
    }
}