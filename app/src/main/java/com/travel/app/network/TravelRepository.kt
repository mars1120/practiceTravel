package com.travel.app.network

import androidx.lifecycle.LiveData
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface ITravelRepository {
    fun getTravelNews(
        lang: String,
        targetDispatchers: CoroutineContext = Dispatchers.IO
    ): Pair<LiveData<Result<TravelNews>>, Job>

    fun getAttractionsAll(
        lang: String,
        targetDispatchers: CoroutineContext = Dispatchers.IO
    ): Pair<LiveData<Result<AttractionsAll>>, Job>
}

class TravelRepositoryImpl : BaseRepository(), ITravelRepository {
    override fun getTravelNews(
        lang: String,
        targetDispatchers: CoroutineContext
    ): Pair<LiveData<Result<TravelNews>>, Job> =
        fire(targetDispatchers) {
            val travelNews = NetworkRequest.getTravelNews(lang)
            Result.success(travelNews)
        }

    override fun getAttractionsAll(
        lang: String,
        targetDispatchers: CoroutineContext
    ) =
        fire(targetDispatchers) {
            val attractionsAll = NetworkRequest.getAttractionsAll(lang)
            Result.success(attractionsAll)
        }
}