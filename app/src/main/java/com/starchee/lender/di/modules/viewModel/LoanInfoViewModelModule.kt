package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.LoanInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoanInfoViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoanInfoViewModel::class)
    fun postLoanInfoViewModel(viewModel: LoanInfoViewModel): ViewModel
}