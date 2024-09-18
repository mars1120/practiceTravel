package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.app.network.TravelApi
import kotlinx.coroutines.launch

class HomepageViewModel :ViewModel(){
    private val _total = MutableLiveData<Int>()

    val total: LiveData<Int> = _total
    init {
        getTravelNews()
    }



    private fun getTravelNews() {
        // _status.value = "Set the Mars API status response here!"
        viewModelScope.launch {
//            _status.value = MarsApiStatus.LOADING
            try {
                _total.value = TravelApi.retrofitService.getNews().total

            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
//                _photos.value = listOf()
            }

        }
    }
}
