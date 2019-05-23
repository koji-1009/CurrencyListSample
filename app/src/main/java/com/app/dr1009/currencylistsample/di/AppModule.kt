package com.app.dr1009.currencylistsample.di

import com.app.dr1009.currencylistsample.api.CurrencyMockService
import com.app.dr1009.currencylistsample.api.CurrencyService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://apilayer.net/api")
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()

    /*
    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService = retrofit.create(CurrencyService::class.java)
     */

    @Singleton
    @Provides
    fun provideCurrencyService(): CurrencyService = CurrencyMockService()
}