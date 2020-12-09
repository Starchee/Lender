package com.starchee.lender.di.components

import android.app.Application
import android.content.SharedPreferences
import com.starchee.lender.data.dataSource.loan.LoanApi
import com.starchee.lender.data.dataSource.loan.LoanDao
import com.starchee.lender.di.modules.app.*
import com.starchee.lender.di.modules.loan.loanApplication.CreateLoanAppUseCaseModule
import com.starchee.lender.di.modules.local.LocalDataSourceModuleImpl
import com.starchee.lender.di.modules.local.LocalRepositoryModule
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.data.utils.DateUtils
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        LoanApiModule::class,
        LoanDatabaseModule::class,
        LocalLoanDataSourceImplModule::class,
        RemoteLoanDataSourceModuleImpl::class,
        RemoteLoanRepositoryModule::class,
        LocalLoanRepositoryModule::class,
        CreateLoanAppUseCaseModule::class,
        LocalRepositoryModule::class,
        LocalDataSourceModuleImpl::class,
        UtilsModule::class,
        SaveLocaleUseCaseModule::class
    ]
)
interface AppComponent {

    fun retrofit(): Retrofit
    fun loanApi(): LoanApi
    fun loanDao(): LoanDao
    fun sharedPreferences(): SharedPreferences
    fun localRepository(): LocalRepository
    fun remoteLoanRepository(): RemoteLoanRepository
    fun localLoanRepository(): LocalLoanRepository
    fun dateConverter(): DateUtils
    fun saveLocaleUseCase(): SaveLocaleUseCase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun withApplication(application: Application): Builder
        fun build(): AppComponent
    }
}