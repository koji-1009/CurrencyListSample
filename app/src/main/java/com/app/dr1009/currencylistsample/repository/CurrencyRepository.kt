package com.app.dr1009.currencylistsample.repository

import com.app.dr1009.currencylistsample.api.CurrencyService
import com.app.dr1009.currencylistsample.dao.CurrencyDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {

    companion object {
        private const val ACCESS_KEY = "test"
    }

    val currencyList = currencyDao.findCurrency()

    suspend fun fetchCurrency() {
        val response = currencyService.getCurrencies(ACCESS_KEY)
        val currencyMap = response.quotes
    }
}