package com.app.dr1009.currencylistsample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val pair: String,
    val rate: Double,
    val source: String
) {
    companion object {
        fun create(response: CurrencyResponse) = response.quotes.map { Currency(pair = it.key, rate = it.value, source = response.source) }
    }
}

@Entity(tableName = "currency_response_timestamp")
data class CurrencyResponseTimestamp(
    @PrimaryKey
    val source: String,
    val timestamp: Long
) {
    companion object {
        fun create(response: CurrencyResponse) =
            CurrencyResponseTimestamp(source = response.source, timestamp = response.timestamp)
    }

    fun getTimestampInstance() = Timestamp(timestamp)
}