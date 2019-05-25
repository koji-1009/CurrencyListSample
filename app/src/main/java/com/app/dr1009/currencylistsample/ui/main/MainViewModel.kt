package com.app.dr1009.currencylistsample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dr1009.currencylistsample.repository.CurrencyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    init {
        fetchCurrencyList()
    }

    val currencyList = repository.currencyList
    val error = MutableLiveData<Throwable>()

    fun fetchCurrencyList(source: String = DEFAULT_SOURCE) {
        viewModelScope.launch {
            runCatching {
                repository.fetchCurrency(source)
            }.onFailure { error.postValue(it) }
        }
    }

    companion object {
        private const val DEFAULT_SOURCE = "USD"
    }
}
