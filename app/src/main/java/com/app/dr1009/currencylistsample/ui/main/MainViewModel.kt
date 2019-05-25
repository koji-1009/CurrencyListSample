package com.app.dr1009.currencylistsample.ui.main

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.app.dr1009.currencylistsample.entity.Currency
import com.app.dr1009.currencylistsample.entity.SelectableSource
import com.app.dr1009.currencylistsample.repository.CurrencyRepository
import com.app.dr1009.currencylistsample.util.map
import com.app.dr1009.currencylistsample.util.switchMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    val selectableSourceList = SelectableSource.values().map { it.getCodeAndName() }
    val spinnerSelectPosition = object : MutableLiveData<Int?>() {

        init {
            value = 0
        }

        override fun setValue(value: Int?) {
            if (this.value != value) super.setValue(value)
        }

        override fun postValue(value: Int?) {
            if (this.value != value) super.postValue(value)
        }
    }
    private val selectSource = spinnerSelectPosition.map { SelectableSource.values()[it ?: 0] }

    val currencyList: LiveData<List<Currency>> = selectSource.switchMap {
        fetchCurrencyList(it)

        return@switchMap repository.currencyList(it.currencyCode)
    }
    val networkError = MutableLiveData<Throwable>()

    private val selectedCurrency: MediatorLiveData<Currency?> = MediatorLiveData()
    val currencyRate: LiveData<String> = selectedCurrency.map { it?.getRateStr() ?: "" }

    init {
        fetchCurrencyList()

        selectedCurrency.addSource(currencyList) {
            selectedCurrency.postValue(
                if (it.isNotEmpty()) {
                    it[0]
                } else {
                    null
                }
            )
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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.fetchCurrency(source.currencyCode)
            }.onFailure {
                networkError.postValue(it)
            }
        }
    }

    private fun SelectableSource.getCodeAndName() = "$currencyCode : $currencyName"

    private fun Currency.getRateStr() = "1 " + pair.substring(0, 3) + " = " + rate + " " + pair.substring(3)
}
