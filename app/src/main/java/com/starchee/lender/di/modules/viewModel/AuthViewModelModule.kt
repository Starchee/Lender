package com.starchee.lender.di.modules.viewModel

import androidx.lifecycle.ViewModel
import com.starchee.lender.viewModels.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun postAuthViewModel(viewModel: AuthViewModel): ViewModel
}