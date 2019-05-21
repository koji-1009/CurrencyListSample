package com.app.dr1009.currencylistsample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideContext(): Context = application.applicationContext
}