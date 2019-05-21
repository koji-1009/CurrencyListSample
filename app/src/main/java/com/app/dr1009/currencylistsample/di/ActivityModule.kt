package com.app.dr1009.currencylistsample.di

import com.app.dr1009.currencylistsample.ui.main.MainActivity
import com.app.dr1009.currencylistsample.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
}