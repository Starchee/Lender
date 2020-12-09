package com.starchee.lender.di.components

import com.starchee.lender.di.modules.mainActivity.GetSavedLocaleUseCaseModule
import com.starchee.lender.di.modules.viewModel.MainActivityViewModelModule
import com.starchee.lender.di.scopes.MainActivityScope
import com.starchee.lender.ui.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    modules = [
        GetSavedLocaleUseCaseModule::class,
        MainActivityViewModelModule::class
    ],
    dependencies = [AppComponent::class]
)
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MainActivityComponent
    }
}