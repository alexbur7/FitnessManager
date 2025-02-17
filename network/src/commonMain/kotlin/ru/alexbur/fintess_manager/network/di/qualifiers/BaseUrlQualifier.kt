package ru.alexbur.fintess_manager.network.di.qualifiers

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

internal fun baseUrl(): Qualifier = BaseUrlQualifier()

private class BaseUrlQualifier : Qualifier {
    override val value: QualifierValue
        get() = "BaseUrl"

    override fun toString(): String {
        return value
    }
}