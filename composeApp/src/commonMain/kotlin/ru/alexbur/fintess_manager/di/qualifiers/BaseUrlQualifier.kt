package ru.alexbur.fintess_manager.di.qualifiers

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

fun baseUrl(): Qualifier = BaseUrlQualifier()

private class BaseUrlQualifier : Qualifier {
    override val value: QualifierValue
        get() = "BaseUrl"

    override fun toString(): String {
        return value
    }
}