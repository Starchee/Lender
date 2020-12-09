package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.LoanListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoanListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoanListViewModel::class)
    fun postLoanListViewModel(viewModel: LoanListViewModel): ViewModel
}