package com.app.dr1009.currencylistsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.dr1009.currencylistsample.dao.CurrencyDao
import com.app.dr1009.currencylistsample.entity.Currency
import com.app.dr1009.currencylistsample.entity.CurrencyResponseTimestamp

@Database(entities = [Currency::class, CurrencyResponseTimestamp::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}