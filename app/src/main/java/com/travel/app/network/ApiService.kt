package com.travel.app.network

import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("open-api/{lang}/Events/News")
    fun getTravelNews(
        @Path("lang") lang: String = "zh-tw",
        @Query("begin") begin: String = "2000-01-01",
        @Query("end") end: String = "2024-09-12",
        @Query("per_page") perPage: Int = 1
    ): Call<TravelNews>

    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("open-api/{lang}/Attractions/All")
    fun getAttractionsAll(
        @Path("lang") lang: String = "zh-tw"
    ): Call<AttractionsAll>
}