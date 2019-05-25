package com.app.dr1009.currencylistsample.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.dr1009.currencylistsample.entity.Currency

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insert(currencyList: List<Currency>)

    @Query("SELECT * FROM currency")
    fun findCurrency(): LiveData<List<Currency>>
}