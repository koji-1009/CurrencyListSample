package com.app.dr1009.currencylistsample.entity

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val success: Boolean, // true
    val terms: String, // https://currencylayer.com/terms
    val privacy: String, // https://currencylayer.com/privacy
    val timestamp: Long, // 1430401802
    val source: String, // USD
    val quotes: Map<String, Double>
)