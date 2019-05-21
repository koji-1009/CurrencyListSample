package com.app.dr1009.currencylistsample.repository

import com.app.dr1009.currencylistsample.api.CurrencyService
import javax.inject.Singleton

@Singleton
class CurrencyRepository(private val currencyService: CurrencyService) {

    companion object {
        private const val ACCESS_KEY = "test"
    }

    suspend fun fetchCurrency() {
        val resposne = currencyService.getCurrencies(ACCESS_KEY)
    }
}