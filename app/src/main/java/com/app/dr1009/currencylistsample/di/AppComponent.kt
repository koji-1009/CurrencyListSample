package com.app.dr1009.currencylistsample.di

import com.app.dr1009.currencylistsample.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class]
)
interface AppComponent : AndroidInjector<App>