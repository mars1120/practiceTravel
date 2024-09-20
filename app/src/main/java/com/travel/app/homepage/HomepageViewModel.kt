package com.travel.app.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import com.travel.app.network.TravelRepositoryImpl
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicInteger

class HomepageViewModel(
    private val repository: ITravelRepository = TravelRepositoryImpl()
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _clickedItem = MutableLiveData<Int>(-1)
    val clickedItem: LiveData<Int> = _clickedItem

    private var activeJobs = AtomicInteger(0)

    fun setClickedItem(item: Int) {
        _clickedItem.value = item
    }

    private val _currentTitle = MutableLiveData<String>()
    val currentTitle: LiveData<String> = _currentTitle

    fun setCurrentTitle(title: String) {
        _currentTitle.value = title
    }


    fun setLangChanging(isChanging: Boolean) {
        if (isChanging) {
            _isLoading.value = true
        }
    }

    private val _travelnewsResult = MediatorLiveData<Result<TravelNews>?>()
    val travelnewsResult: LiveData<Result<TravelNews>?> = _travelnewsResult

    private val _attractionsResult = MediatorLiveData<Result<AttractionsAll>?>()
    val attractionsResult: LiveData<Result<AttractionsAll>?> = _attractionsResult

    private var travelNewsJob: Job? = null
    private var attractionsJob: Job? = null


    fun fetchData(lang: String) {
        travelNewsJob?.cancel()
        attractionsJob?.cancel()
        activeJobs.set(2)
        val (travelNewsLiveData, travelNewsJobNew) = repository.getTravelNews(lang)
        travelNewsJob = travelNewsJobNew
        _travelnewsResult.addSource(travelNewsLiveData) { result ->
            result.fold(
                onSuccess = { response ->
                    _travelnewsResult.value = result
                    checkLoadingComplete()
                },
                onFailure = {
                    if (it !is CancellationException)
                        checkLoadingComplete()
                }
            )

        }

        val (attractionsLiveData, attractionsJobNew) = repository.getAttractionsAll(lang)
        attractionsJob = attractionsJobNew
        _attractionsResult.addSource(attractionsLiveData) { result ->
            result.fold(
                onSuccess = { response ->
                    _attractionsResult.value = result
                    checkLoadingComplete()
                },
                onFailure = {
                    if (it !is CancellationException)
                        checkLoadingComplete()
                }
            )
        }
    }

    private fun checkLoadingComplete() {
        if (activeJobs.decrementAndGet() == 0) {
            _isLoading.postValue(false)
        }
    }
}