package com.app.dr1009.currencylistsample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val pair: String,
    val rate: Double
)