package ru.alexbur.fintess_manager.feature.login.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.fintess_manager.common_presentation.mediators.PreferenceMediator
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.feature.login.data.api.LoginApi
import ru.alexbur.fintess_manager.feature.login.data.mapper.LoginMapper
import ru.alexbur.fintess_manager.feature.login.domain.models.GetOtp
import ru.alexbur.fintess_manager.feature.login.domain.models.Login
import ru.alexbur.fintess_manager.feature.login.domain.repository.LoginRepository

internal class LoginRepositoryImpl(
    private val api: LoginApi,
    private val mapper: LoginMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val preferenceMediator: PreferenceMediator,
) : LoginRepository {

    override suspend fun getOtp(phoneNumber: String): GetOtp = withContext(dispatcherProvider.io()) {
        mapper.map(api.getOtp(phoneNumber))
    }

    override suspend fun login(
        userId: Long,
        otp: String
    ): Login = withContext(dispatcherProvider.io()) {
        mapper.map(api.login(userId, otp)).also {
            preferenceMediator.accessToken = it.accessToken
            preferenceMediator.refreshToken = it.refreshToken
        }
    }
}