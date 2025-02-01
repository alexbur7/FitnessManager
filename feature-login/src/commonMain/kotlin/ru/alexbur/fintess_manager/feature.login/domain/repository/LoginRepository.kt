package ru.alexbur.fintess_manager.feature.login.domain.repository

import ru.alexbur.fintess_manager.feature.login.domain.models.GetOtp
import ru.alexbur.fintess_manager.feature.login.domain.models.Login

internal interface LoginRepository {
    suspend fun getOtp(phoneNumber: String): GetOtp
    suspend fun login(userId: Long, otp: String): Login
}