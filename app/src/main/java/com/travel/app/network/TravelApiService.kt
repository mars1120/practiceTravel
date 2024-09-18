package com.travel.app.network

import com.travel.app.data.TravelNews
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL = "https://www.travel.taipei"

private val retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
        .build()

interface TravelApiService {
    @Headers("Content-type: application/json", "Accept: application/json")
    @GET("open-api/zh-tw/Events/News")
    suspend fun getNews(
        @Query("begin") begin: String = "2000-01-01",
        @Query("end") end: String = "2024-09-12",
        @Query("per_page") perPage: Int = 1
    ): TravelNews
//    https://<DOMAIN>/api/users?page=2&per_page=10
//    @GET("/api/users")
//    public Call<UsersApiResponse> getUsers(@Query("per_page") int pageSize,
//    @Query("page") int currentPage);
}

object TravelApi {
    val retrofitService: TravelApiService by lazy {
        retrofit.create(TravelApiService::class.java)
    }

}