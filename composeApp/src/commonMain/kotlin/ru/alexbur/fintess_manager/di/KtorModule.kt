package ru.alexbur.fintess_manager.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.alexbur.fintess_manager.core.AppLogger
import ru.alexbur.fintess_manager.di.qualifiers.baseUrl

val apiModule = module {
    single(baseUrl()) {
        ApiClient.provideBaseUrl()
    }
    single {
        ApiClient.provideJson()
    }
    single {
        ApiClient.provideHttpClient(get(), get(baseUrl()))
    }
}

private object ApiClient {

    fun provideBaseUrl(): String {
        return "https://api.spacexdata.com/"
    }

    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    fun provideHttpClient(json: Json, baseUrl: String): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        ru.alexbur.fintess_manager.core.AppLogger.w("Ktor", message)
                    }
                }
            }
            defaultRequest {
                url(baseUrl)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
            }
        }
    }
}