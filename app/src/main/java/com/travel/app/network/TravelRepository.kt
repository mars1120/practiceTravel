package com.travel.app.network

import androidx.lifecycle.LiveData
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.utils.Constant.CODE
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

interface ITravelRepository {
    fun getTravelNews(
        lang: String,
        targetDispatchers: CoroutineContext = Dispatchers.IO
    ): LiveData<Result<Response<TravelNews>>>

    fun getAttractionsAll(
        lang: String,
        targetDispatchers: CoroutineContext = Dispatchers.IO
    ): LiveData<Result<Response<AttractionsAll>>>
}

class TravelRepositoryImpl : BaseRepository(), ITravelRepository {
    override fun getTravelNews(lang: String, targetDispatchers: CoroutineContext) =
        fire(targetDispatchers) {
            val travelNews = NetworkRequest.getTravelNews(lang)
            if (travelNews.code() == CODE) Result.success(travelNews)
            else Result.failure(RuntimeException("getNews response code is ${travelNews.code()} msg is ${travelNews.message()}"))
        }
    override fun getAttractionsAll(lang: String, targetDispatchers: CoroutineContext) =
        fire(targetDispatchers) {
            val attractionsAll = NetworkRequest.getAttractionsAll(lang)
            if (attractionsAll.code() == CODE) Result.success(attractionsAll)
            else Result.failure(RuntimeException("getAttractions response code is ${attractionsAll.code()} msg is ${attractionsAll.message()}"))
        }
}