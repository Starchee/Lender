package com.starchee.lender.di.components

import com.starchee.lender.di.modules.auth.*
import com.starchee.lender.di.modules.viewModel.AuthViewModelModule
import com.starchee.lender.di.scopes.AuthScope
import com.starchee.lender.ui.AuthFragment
import dagger.Component

@AuthScope
@Component(
    modules = [
        LoginUseCaseModule::class,
        RegistrationUseCaseModule::class,
        AuthApiModule::class,
        AuthRepositoryModule::class,
        AuthDataSourceModuleImpl::class,
        AuthViewModelModule::class,
        LogoutUseCaseModule::class
    ],
    dependencies = [AppComponent::class]
)
interface AuthComponent {

    fun inject(authFragment: AuthFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): AuthComponent
    }
}