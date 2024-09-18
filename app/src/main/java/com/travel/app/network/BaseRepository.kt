package com.travel.app.network

import androidx.lifecycle.liveData
import kotlin.coroutines.CoroutineContext

open class BaseRepository {
    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            //notify
            emit(result)
        }
}
