package com.app.dr1009.currencylistsample.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.dr1009.currencylistsample.api.CurrencyMockService
import com.app.dr1009.currencylistsample.api.CurrencyService
import com.app.dr1009.currencylistsample.dao.CurrencyDao
import com.app.dr1009.currencylistsample.db.AppDatabase
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

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    // region network
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
    // endregion

    // region DB
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "currency_list_db")
        .build()

    @Singleton
    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao()
    // endregion
}