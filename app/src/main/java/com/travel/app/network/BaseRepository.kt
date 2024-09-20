package com.travel.app.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


open class BaseRepository {
    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>): Pair<LiveData<Result<T>>, Job> {
        val result = MutableLiveData<Result<T>>()
        val job = CoroutineScope(context).launch {
            val res = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            //notify
            result.postValue(res)
        }
        return Pair(result, job)
    }
}