package com.app.dr1009.currencylistsample.di

import android.app.Application
import android.content.Context
import com.app.dr1009.currencylistsample.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        NetworkModule::class,
        DbModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

fun Application.createAppComponent() = DaggerAppComponent.factory().create(this)
