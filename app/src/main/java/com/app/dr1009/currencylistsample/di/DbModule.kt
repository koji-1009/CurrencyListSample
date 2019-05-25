package com.app.dr1009.currencylistsample.di

import android.app.Application
import androidx.room.Room
import com.app.dr1009.currencylistsample.dao.CurrencyDao
import com.app.dr1009.currencylistsample.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
        .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao()
}