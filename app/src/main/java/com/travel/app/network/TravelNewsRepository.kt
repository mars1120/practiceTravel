package com.travel.app.network

import androidx.lifecycle.LiveData
import com.travel.app.data.TravelNews
import com.travel.app.utils.Constant.CODE
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

interface ITravelNewsRepository {
    fun getTravelNews(targetDispatchers: CoroutineContext = Dispatchers.IO): LiveData<Result<Response<TravelNews>>>
}

class TravelNewsRepositoryImpl : BaseRepository(), ITravelNewsRepository {
    override fun getTravelNews(targetDispatchers: CoroutineContext) =
        fire(targetDispatchers) {
            val travelNews = NetworkRequest.getTravelNews()
            if (travelNews.code() == CODE) Result.success(travelNews)
            else Result.failure(RuntimeException("getNews response code is ${travelNews.code()} msg is ${travelNews.message()}"))
        }
}