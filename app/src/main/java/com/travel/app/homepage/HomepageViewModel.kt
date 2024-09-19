package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import com.travel.app.network.TravelRepositoryImpl
import kotlinx.coroutines.launch

class HomepageViewModel(
    private val repository: ITravelRepository = TravelRepositoryImpl()
) : ViewModel() {
    private val _clickedItem = MutableLiveData<Int>()
    val clickedItem: LiveData<Int> = _clickedItem

    fun setClickedItem(item: Int) {
        _clickedItem.value = item
    }

    private val _travelnewResult = MediatorLiveData<Result<TravelNews>?>()
    val travelnewsResult: LiveData<Result<TravelNews>?> = _travelnewResult

    private val _attractionsResult = MediatorLiveData<Result<AttractionsAll>?>()
    val attractionsResult: LiveData<Result<AttractionsAll>?> = _attractionsResult

    fun fetchData() {
        viewModelScope.launch {
            _travelnewResult.addSource(repository.getTravelNews()) { result ->
                _travelnewResult.value = result.fold(
                    onSuccess = { response ->
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception("API error: ${response.code()}"))
                        }
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                )
            }

            _attractionsResult.addSource(repository.getAttractionsAll()) { result ->
                _attractionsResult.value = result.fold(
                    onSuccess = { response ->
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception("API error: ${response.code()}"))
                        }
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                )
            }
        }
    }
}