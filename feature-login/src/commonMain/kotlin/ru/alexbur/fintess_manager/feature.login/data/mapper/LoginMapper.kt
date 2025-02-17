package ru.alexbur.fintess_manager.feature.login.data.mapper

import ru.alexbur.fintess_manager.feature.login.data.models.response.GetOtpResponse
import ru.alexbur.fintess_manager.feature.login.data.models.response.LoginResponse
import ru.alexbur.fintess_manager.feature.login.domain.models.GetOtp
import ru.alexbur.fintess_manager.feature.login.domain.models.Login

internal class LoginMapper {

    fun map(response: GetOtpResponse) = GetOtp(
        userId = response.userId
    )

    fun map(response: LoginResponse) = Login(
        accessToken = response.accessToken,
        refreshToken = response.refreshToken,
    )
}