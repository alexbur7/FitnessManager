package ru.alexbur.fintess_manager.feature.login.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.alexbur.fintess_manager.feature.login.data.models.request.GetOtpRequest
import ru.alexbur.fintess_manager.feature.login.data.models.request.LoginRequest
import ru.alexbur.fintess_manager.feature.login.data.models.response.GetOtpResponse
import ru.alexbur.fintess_manager.feature.login.data.models.response.LoginResponse

internal class LoginApi(
    private val client: HttpClient,
) {

    suspend fun getOtp(phoneNumber: String): GetOtpResponse {
        return client.post("login/get-otp") {
            contentType(ContentType.Application.Json)
            setBody(
                GetOtpRequest(
                    phoneNumber = phoneNumber
                )
            )
        }.body()
    }

    suspend fun login(userId: Long, otp: String): LoginResponse {
        return client.post("login/send-otp") {
            contentType(ContentType.Application.Json)
            setBody(
                LoginRequest(userId = userId, otp = otp)
            )
        }.body()
    }
}