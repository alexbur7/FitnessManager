package ru.alexbur.fintess_manager.feature.login.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class GetOtpRequest(
    @SerialName("phone_number")
    val phoneNumber: String
)