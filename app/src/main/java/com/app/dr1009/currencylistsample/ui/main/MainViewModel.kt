package com.app.dr1009.currencylistsample.ui.main

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

   fun fetchCurrencyList() {
      viewModelScope.launch {
         repository.fetchCurrency()
      }
   }
}
