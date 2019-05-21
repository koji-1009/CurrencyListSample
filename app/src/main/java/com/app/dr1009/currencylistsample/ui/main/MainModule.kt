package com.app.dr1009.currencylistsample.ui.main

import androidx.lifecycle.ViewModel
import com.app.dr1009.currencylistsample.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainFragment(): MainFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindLaunchViewModel(viewModel: MainViewModel): ViewModel
}