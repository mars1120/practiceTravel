package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import com.travel.app.network.TravelRepositoryImpl
import retrofit2.Response
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

    fun fetchData(lang: String) {

        _isLoading.value = true

        activeJobs.set(2)

        fetchDataSafely(_travelnewsResult) { repository.getTravelNews(lang) }
        fetchDataSafely(_attractionsResult) { repository.getAttractionsAll(lang) }
    }

    private fun <T> fetchDataSafely(
        resultLiveData: MediatorLiveData<Result<T>?>,
        apiCall: () -> LiveData<Result<Response<T>>>
    ) {
        resultLiveData.addSource(apiCall()) { result ->
            result.fold(
                onSuccess = { response ->
                    resultLiveData.value = if (response.isSuccessful) {
                        Result.success(response.body()!!)
                    } else {
                        Result.failure(Exception("API error: ${response.code()}"))
                    }
                },
                onFailure = {
                    resultLiveData.value = Result.failure(it)
                }
            )
            checkLoadingComplete()
        }
    }

    private fun checkLoadingComplete() {
        if (activeJobs.decrementAndGet() == 0) {
            _isLoading.postValue(false)
        }
    }
}