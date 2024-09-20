package com.travel.app.network

import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ITravelRepository {
    fun getTravelNews(lang: String): Flow<Result<TravelNews>>
    fun getAttractionsAll(lang: String): Flow<Result<AttractionsAll>>
}

class TravelRepositoryImpl : ITravelRepository {
    override fun getTravelNews(lang: String): Flow<Result<TravelNews>> = flow {
        val travelNews = NetworkRequest.getTravelNews(lang)
        emit(Result.success(travelNews))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }

    override fun getAttractionsAll(lang: String): Flow<Result<AttractionsAll>> = flow {
        val attractionsAll = NetworkRequest.getAttractionsAll(lang)
        emit(Result.success(attractionsAll))
    }.flowOn(Dispatchers.IO)
        .catch { e ->
            emit(Result.failure(e))
        }
}