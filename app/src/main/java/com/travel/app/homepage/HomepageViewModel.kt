package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelNewsRepository
import com.travel.app.network.TravelNewsRepositoryImpl
import kotlinx.coroutines.launch

class HomepageViewModel(
    private val repository: ITravelNewsRepository = TravelNewsRepositoryImpl()
) : ViewModel() {
    private val _clickedItem = MutableLiveData<Int>()
    val clickedItem: LiveData<Int> = _clickedItem

    fun setClickedItem(item: Int) {
        _clickedItem.value = item
    }

    private val _resultA = MediatorLiveData<Result<TravelNews>?>()
    val resultA: LiveData<Result<TravelNews>?> = _resultA

    private val _resultB = MediatorLiveData<Result<TravelNews>?>()
    val resultB: LiveData<Result<TravelNews>?> = _resultB

    fun fetchData() {
        viewModelScope.launch {
            val sourceA = repository.getTravelNews()
            _resultA.addSource(sourceA) { result ->
                _resultA.value = result.fold(
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

            val sourceB = repository.getTravelNews()
            _resultB.addSource(sourceB) { result ->
                _resultB.value = result.fold(
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