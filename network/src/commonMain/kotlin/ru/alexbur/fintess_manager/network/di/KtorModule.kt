package ru.alexbur.fintess_manager.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.alexbur.fintess_manager.core.AppLogger
import ru.alexbur.fintess_manager.network.data.converter.ApiConverter
import ru.alexbur.fintess_manager.network.di.qualifiers.baseUrl

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
        return "http://192.168.1.40:8080/"
    }

    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    fun provideHttpClient(json: Json, baseUrl: String): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                register(ContentType.Application.Json, ApiConverter(json))
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        AppLogger.w("Ktor", message)
                    }
                }
                level = LogLevel.ALL
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