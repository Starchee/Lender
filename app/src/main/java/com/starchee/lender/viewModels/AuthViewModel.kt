package com.starchee.lender.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.useCases.auth.LoginUseCase
import com.starchee.lender.domain.useCases.auth.LogoutUseCase
import com.starchee.lender.domain.useCases.auth.RegistrationUseCase
import com.starchee.lender.viewModels.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _networkErrors = SingleLiveEvent<NetworkError>()
    private val _isLogin = SingleLiveEvent<AuthState>()

    val networkErrors: LiveData<NetworkError> = _networkErrors
    val isLogin: LiveData<AuthState> = _isLogin

    companion object {
        val TAG = AuthViewModel::class.java.name
    }

    fun logout() {
        val result = logoutUseCase().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isLogin.value = AuthState.LOGOUT
            },
                {
                Log.e(TAG, it.toString())
            })
        compositeDisposable.add(result)
    }

    fun login(name: String, password: String) {
        val result = loginUseCase(User(name, password)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isLogin.value = AuthState.LOGIN
            }, {
                checkException(it)
            })
        compositeDisposable.add(result)
    }

    fun registration(name: String, password: String) {
        val result = registrationUseCase(User(name, password)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isLogin.value = AuthState.REGISTERED
            }, {
                checkException(it)
            })
        compositeDisposable.add(result)
    }

    private fun checkException(exception: Throwable) {
        when (exception) {
            is NoNetWorkException -> _networkErrors.value = NetworkError.NETWORK
            is BadRequestException -> _networkErrors.value = NetworkError.BAD_REQUEST
            is NotFoundException -> _networkErrors.value = NetworkError.NOT_FOUND
            else -> Log.e(TAG, exception.toString())
        }
    }
}