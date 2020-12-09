package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun postMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}