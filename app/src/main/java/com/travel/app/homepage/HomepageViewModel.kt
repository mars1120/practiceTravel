package com.travel.app.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import com.travel.app.network.TravelRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch


data class HomepageUiState(
    val isLoading: Boolean = true,
    val travelNews: TravelNews? = null,
    val attractions: AttractionsAll? = null,
    val error: String? = null,
    val clickedItem: Int = -1
)

class HomepageViewModel(
    private val repository: ITravelRepository = TravelRepositoryImpl()
) : ViewModel() {

    private val _currentTitle = MutableLiveData<String>()
    val currentTitle: LiveData<String> = _currentTitle

    private val _uiState = MutableStateFlow(HomepageUiState())
    val uiState: StateFlow<HomepageUiState> = _uiState.asStateFlow()

    private var currentLanguage: String? = null
    private var fetchJob: Job? = null

    fun fetchData(lang: String) {

        currentLanguage = lang

        fetchJob?.cancelChildren()

        fetchJob = viewModelScope.launch {

            repository.getTravelNews(lang)
                .zip(repository.getAttractionsAll(lang)) { newsResult, attractionsResult ->
                    newsResult to attractionsResult
                }
                .cancellable()
                .collect { (newsResult, attractionsResult) ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            travelNews = newsResult.getOrNull(),
                            attractions = attractionsResult.getOrNull(),
                            error = when {
                                newsResult.isFailure -> newsResult.exceptionOrNull()?.message
                                attractionsResult.isFailure -> attractionsResult.exceptionOrNull()?.message
                                else -> null
                            }
                        )
                    }
                }
        }
    }

    fun setCurrentTitle(title: String) {
        _currentTitle.value = title
    }

    fun setClickedItem(item: Int) {
        _uiState.update { it.copy(clickedItem = item) }
    }

    fun setLangChanging(isChange: Boolean) {
        _uiState.update { it.copy(isLoading = isChange) }
    }
}