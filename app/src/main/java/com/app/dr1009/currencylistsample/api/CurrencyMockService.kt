package com.app.dr1009.currencylistsample.api

import com.app.dr1009.currencylistsample.entity.CurrencyResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse

class CurrencyMockService : CurrencyService {
    @ImplicitReflectionSerializer
    override fun getCurrencies(accessKey: String, source: String): Deferred<CurrencyResponse> {
        return getJsonData()
    }

    @ImplicitReflectionSerializer
    private fun getJsonData(): Deferred<CurrencyResponse> {
        return GlobalScope.async {
            // 1秒遅延
            delay(1000)
            return@async Json.parse<CurrencyResponse>(JSON_TEST)
        }
    }

    companion object {
        private const val JSON_TEST =
"""{
    "success": true,
    "terms": "https://currencylayer.com/terms",
    "privacy": "https://currencylayer.com/privacy",
    "timestamp": 1430401802,
    "source": "USD",
    "quotes": {
        "USDAED": 3.672982,
        "USDAFN": 57.8936,
        "USDALL": 126.1652,
        "USDAMD": 475.306,
        "USDANG": 1.78952,
        "USDAOA": 109.216875,
        "USDARS": 8.901966,
        "USDAUD": 1.269072,
        "USDAWG": 1.792375,
        "USDAZN": 1.04945,
        "USDBAM": 1.757305,
    }
}"""
    }
}