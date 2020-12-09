package com.starchee.lender.di.components

import com.starchee.lender.di.modules.loan.loanApplication.CreateLoanAppUseCaseModule
import com.starchee.lender.di.modules.loan.loanApplication.GetLoanConditionCaseModule
import com.starchee.lender.di.scopes.LoanAppScope
import com.starchee.lender.di.modules.viewModel.LoanAppViewModelModule
import com.starchee.lender.ui.LoanAppFragment
import dagger.Component

@LoanAppScope
@Component(
    modules = [
        CreateLoanAppUseCaseModule::class,
        GetLoanConditionCaseModule::class,
        LoanAppViewModelModule::class
    ],
    dependencies = [AppComponent::class]
)
interface LoanAppComponent {

    fun inject(loanAppFragment: LoanAppFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): LoanAppComponent
    }
}