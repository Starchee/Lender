package com.starchee.lender.di.components

import com.starchee.lender.di.modules.loan.loanList.GeRemoteLoanListUseCaseModule
import com.starchee.lender.di.modules.loan.loanList.GetLocalLoanListUseCaseModule
import com.starchee.lender.di.modules.viewModel.LoanListViewModelModule
import com.starchee.lender.di.scopes.LoanListScope
import com.starchee.lender.ui.LoanListFragment
import dagger.Component


@LoanListScope
@Component(
    modules = [
        GetLocalLoanListUseCaseModule::class,
        GeRemoteLoanListUseCaseModule::class,
        LoanListViewModelModule::class
    ],
    dependencies = [AppComponent::class]
)
interface LoanListComponent {

    fun inject(loanListFragment: LoanListFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): LoanListComponent
    }
}