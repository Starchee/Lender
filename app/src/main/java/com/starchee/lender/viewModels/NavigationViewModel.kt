package com.starchee.lender.viewModels

import androidx.lifecycle.LiveData
import com.starchee.lender.domain.useCases.auth.IsLoginUseCase
import com.starchee.lender.viewModels.utils.SingleLiveEvent
import javax.inject.Inject

class NavigationViewModel @Inject constructor(private val isLoginUseCase: IsLoginUseCase) :
    BaseViewModel() {

    private val _isLogin = SingleLiveEvent<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    init {
        isLogin()
    }

    private fun isLogin() {
        _isLogin.value = isLoginUseCase()
    }
}