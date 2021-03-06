package com.app.dr1009.currencylistsample.api

import com.app.dr1009.currencylistsample.entity.CurrencyResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("live")
    suspend fun getCurrencies(
        @Query("access_key") accessKey: String,
        @Query("source") source: String
    ): CurrencyResponse
}