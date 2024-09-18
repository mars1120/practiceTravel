package com.travel.app.network

import com.travel.app.utils.Constant.CODE
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object TravelNewsRepository : BaseRepository() {
    fun getTravelNews(targetDispatchers: CoroutineContext = Dispatchers.IO) =
        fire(targetDispatchers) {
            val travelNews = NetworkRequest.getTravelNews()

            if (travelNews.code() == CODE) Result.success(travelNews)
            else Result.failure(RuntimeException("getNews response code is ${travelNews.code()} msg is ${travelNews.message()}"))
        }
}