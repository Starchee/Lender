package com.starchee.lender.di.components

import com.starchee.lender.di.modules.loanInfo.GetLocalLoanUseCaseModule
import com.starchee.lender.di.modules.loanInfo.GetRemoteLoanUseCaseModule
import com.starchee.lender.di.modules.viewModel.LoanInfoViewModelModule
import com.starchee.lender.di.scopes.LoanInfoScope
import com.starchee.lender.ui.LoanInfoFragment
import dagger.Component


@LoanInfoScope
@Component(
    modules = [
        GetLocalLoanUseCaseModule::class,
        GetRemoteLoanUseCaseModule::class,
        LoanInfoViewModelModule::class
    ],
    dependencies = [AppComponent::class]
)
interface LoanInfoComponent {

    fun inject(loansInfoFragment: LoanInfoFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): LoanInfoComponent
    }
}