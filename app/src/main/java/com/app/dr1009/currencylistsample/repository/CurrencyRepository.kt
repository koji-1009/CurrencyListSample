package com.app.dr1009.currencylistsample.repository

import com.app.dr1009.currencylistsample.api.CurrencyService
import com.app.dr1009.currencylistsample.dao.CurrencyDao
import com.app.dr1009.currencylistsample.entity.Currency
import com.app.dr1009.currencylistsample.entity.CurrencyResponseTimestamp
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {

    fun currencyList(source: String) = currencyDao.findCurrency(source)

    suspend fun fetchCurrency(source: String) {
        val isAllow = isAllowedRefresh(source)
        if (!isAllow) return

        val response = currencyService.getCurrencies(ACCESS_KEY, source).await()
        if (!response.success) {
            throw IllegalStateException()
        }

        val timestamp = CurrencyResponseTimestamp.create(response)
        val currencyList = Currency.create(response)

        currencyDao.replaceCurrency(timestamp, currencyList)
    }

    private suspend fun isAllowedRefresh(source: String): Boolean {
        val timestampList = currencyDao.findTimestamp(source)
        if (timestampList.isNotEmpty()) {
            val lastTimestamp = timestampList[0].getTimestampInstance()
            val before30Min = Date(System.currentTimeMillis() - MILLIS_30_MINUTES)
            if (!lastTimestamp.before(before30Min)) {
                // refreshed no more frequently than every 30 minutes (to limit bandwidth usage)
                return false
            }
        }

        return true
    }

    companion object {
        private const val ACCESS_KEY = "test"
        private const val MILLIS_30_MINUTES = 30 * 60 * 1000
    }
}