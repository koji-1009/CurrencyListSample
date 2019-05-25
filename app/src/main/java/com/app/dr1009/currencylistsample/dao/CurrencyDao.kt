package com.app.dr1009.currencylistsample.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.dr1009.currencylistsample.entity.Currency
import com.app.dr1009.currencylistsample.entity.CurrencyResponseTimestamp

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insert(currencyList: List<Currency>)

    @Query("DELETE FROM currency")
    suspend fun deleteCurrency()

    @Transaction
    suspend fun replaceCurrency(timestamp: CurrencyResponseTimestamp, currencyList: List<Currency>) {
        insert(timestamp)

        deleteCurrency()
        insert(currencyList)
    }

    @Query("SELECT * FROM currency")
    fun findCurrency(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timestamp: CurrencyResponseTimestamp)

    @Query("SELECT * FROM currency_response_timestamp WHERE source=:source")
    suspend fun findTimestamp(source: String): List<CurrencyResponseTimestamp>
}