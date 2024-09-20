package com.travel.app.network

object NetworkRequest {

    /**
     * create service
     */
    private val service = ServiceCreator.create(ApiService::class.java)

    suspend fun getTravelNews(lang: String) = service.getTravelNews(lang = lang)
    suspend fun getAttractionsAll(lang: String) = service.getAttractionsAll(lang = lang)
}

