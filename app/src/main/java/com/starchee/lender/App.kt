package com.starchee.lender

import android.app.Application
import com.starchee.lender.di.components.*


class App : Application() {

    lateinit var appComponent: AppComponent
    lateinit var authComponent: AuthComponent
    lateinit var loanAppComponent: LoanAppComponent
    lateinit var loanListComponent: LoanListComponent
    lateinit var loanInfoComponent: LoanInfoComponent
    lateinit var navigationComponent: NavigationComponent
    lateinit var mainActivityComponent: MainActivityComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().withApplication(this).build()
        authComponent = DaggerAuthComponent.builder().appComponent(appComponent).build()
        loanAppComponent = DaggerLoanAppComponent.builder().appComponent(appComponent).build()
        loanListComponent = DaggerLoanListComponent.builder().appComponent(appComponent).build()
        loanInfoComponent = DaggerLoanInfoComponent.builder().appComponent(appComponent).build()
        navigationComponent = DaggerNavigationComponent.builder().appComponent(appComponent).build()
        mainActivityComponent = DaggerMainActivityComponent.builder().appComponent(appComponent).build()
    }
}