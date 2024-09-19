package com.travel.app.network

object NetworkRequest {

    /**
     * create service
     */
    private val service = ServiceCreator.create(ApiService::class.java)

    suspend fun getTravelNews() = service.getTravelNews()
    suspend fun getAttractionsAll() = service.getAttractionsAll()
}

