package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.LoanListViewModel
import com.starchee.lender.viewModels.NavigationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NavigationViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    fun postNavigationViewModel(viewModel: NavigationViewModel): ViewModel
}