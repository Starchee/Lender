package com.starchee.lender.di.components

import com.starchee.lender.di.modules.navigation.IsLoginUseCaseModule
import com.starchee.lender.di.modules.viewModel.NavigationViewModelModule
import com.starchee.lender.di.scopes.NavigationScope
import com.starchee.lender.ui.NavigationFragment
import dagger.Component


@NavigationScope
@Component(
    modules = [
        IsLoginUseCaseModule::class,
        NavigationViewModelModule::class
    ],
    dependencies = [AppComponent::class]
)
interface NavigationComponent {

    fun inject(navigationFragment: NavigationFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): NavigationComponent
    }
}