package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.LoanAppViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoanAppViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoanAppViewModel::class)
    fun postLoanAppViewModel(viewModel: LoanAppViewModel): ViewModel
}