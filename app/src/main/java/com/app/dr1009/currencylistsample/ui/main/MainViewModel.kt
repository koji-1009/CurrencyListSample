package com.app.dr1009.currencylistsample.ui.main

import androidx.lifecycle.ViewModel
import com.app.dr1009.currencylistsample.repository.CurrencyRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
   private val repository: CurrencyRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}
