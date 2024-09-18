package com.travel.app.network

import com.travel.app.utils.Constant.CODE
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object TravelNewsRepository:BaseRepository() {
    fun getTravelNews(targetDispatchers: CoroutineContext = Dispatchers.IO) = fire(targetDispatchers) {
        val travelNews = NetworkRequest.getTravelNews()

        Result.success(travelNews)
        //todo: judgment result code and message
//        if (travelNews.total == CODE) Result.success(travelNews)
//        else Result.failure(RuntimeException("getNews response code is ${travelNews.total} msg is ${travelNews.total}"))
    }
}