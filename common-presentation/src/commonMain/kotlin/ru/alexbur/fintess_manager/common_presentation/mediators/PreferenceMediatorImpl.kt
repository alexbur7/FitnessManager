package ru.alexbur.fintess_manager.common_presentation.mediators

import com.russhwolf.settings.Settings

internal class PreferenceMediatorImpl() : PreferenceMediator {

    private val settings: Settings by lazy { Settings() }

    override var accessToken: String
        get() = settings.getString(ACCESS_TOKEN_KEY, "")
        set(value) {
            settings.putString(ACCESS_TOKEN_KEY, value)
        }
    override var refreshToken: String
        get() = settings.getString(REFRESH_TOKEN_KEY, "")
        set(value) {
            settings.putString(REFRESH_TOKEN_KEY, value)
        }

    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token_key"
        const val REFRESH_TOKEN_KEY = "refresh_token_key"
    }
}