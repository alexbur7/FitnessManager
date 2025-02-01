package ru.alexbur.fintess_manager.core.utils

import kotlinx.coroutines.CancellationException

inline fun <T> safeRunCatching(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
}