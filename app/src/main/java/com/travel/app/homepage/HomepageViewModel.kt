package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.app.network.TravelApi
import com.travel.app.network.TravelNewsRepository
import kotlinx.coroutines.launch

class HomepageViewModel : ViewModel() {
    val result = TravelNewsRepository.getTravelNews()
    val result1 = TravelNewsRepository.getTravelNews()
}
