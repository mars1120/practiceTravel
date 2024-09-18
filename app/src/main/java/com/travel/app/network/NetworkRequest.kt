package com.travel.app.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetworkRequest {

    /**
     * create service
     */
    private val service = ServiceCreator.create(ApiService::class.java)

    suspend fun getTravelNews() = service.getTravelNews()
}

