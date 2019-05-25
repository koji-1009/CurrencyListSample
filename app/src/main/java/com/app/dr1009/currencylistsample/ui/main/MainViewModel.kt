package com.app.dr1009.currencylistsample.ui.main

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.app.dr1009.currencylistsample.entity.Currency
import com.app.dr1009.currencylistsample.entity.SelectableSource
import com.app.dr1009.currencylistsample.repository.CurrencyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    val selectableSourceList = SelectableSource.values().map { it.getCodeAndName() }
    val spinnerSelectPosition = MutableLiveData<Int>().apply { value = 0 }
    private val selectSource = Transformations.map(spinnerSelectPosition) { SelectableSource.values()[it] }

    val currencyList: LiveData<List<Currency>> = Transformations.switchMap(selectSource) {
        fetchCurrencyList(it)

        return@switchMap repository.currencyList(it.currencyCode)
    }
    val error = MutableLiveData<Throwable>()

    private val selectedCurrency: MediatorLiveData<Currency> = MediatorLiveData()
    val currencyRate: LiveData<String> = Transformations.map(selectedCurrency) {
        "1 " + it.pair.substring(0, 3) + " = " + it.rate + " " + it.pair.substring(3)
    }

    init {
        fetchCurrencyList()

        selectedCurrency.addSource(currencyList) {
            if (it.isNotEmpty()) {
                selectedCurrency.postValue(it[0])
            } else {
                selectedCurrency.postValue(null)
            }
        }
    }

    @MainThread
    fun setCurrency(currency: Currency) {
        selectedCurrency.postValue(currency)
    }

    @MainThread
    fun fetchCurrentCurrencyList() {
        val source = selectSource.value ?: return

        fetchCurrencyList(source)
    }

    private fun fetchCurrencyList(source: SelectableSource = SelectableSource.USD) {
        viewModelScope.launch {
            runCatching {
                repository.fetchCurrency(source.currencyCode)
            }.onFailure { error.postValue(it) }
        }
    }

    private fun SelectableSource.getCodeAndName() = "$currencyCode : $currencyName"
}
