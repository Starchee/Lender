package com.starchee.lender.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.useCases.loanInfo.GetLocalLoanUseCase
import com.starchee.lender.domain.useCases.loanInfo.GetRemoteLoanUseCase
import com.starchee.lender.viewModels.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanInfoViewModel @Inject constructor(
    private val getRemoteLoanUseCase: GetRemoteLoanUseCase,
    private val getLocalLoanUseCase: GetLocalLoanUseCase
) : BaseViewModel() {

    private val _loanInfo = MutableLiveData<Loan>()
    private val _networkErrors = SingleLiveEvent<NetworkError>()
    val loan: LiveData<Loan> = _loanInfo
    val networkErrors: LiveData<NetworkError> = _networkErrors

    companion object {
        val TAG = LoanInfoViewModel::class.java.name
    }


    fun getLoanById(id: Int) {
        val result = getRemoteLoanUseCase(id).subscribeOn(Schedulers.io())
            .doOnError { getLocalLoanInfoById(id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loanInfo.value = it
            }, {
                checkException(it)
            })
        compositeDisposable.add(result)
    }

    private fun getLocalLoanInfoById(id: Int) {
        val result = getLocalLoanUseCase(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loanInfo.value = it
            }, {
                Log.e(TAG, it.toString())
            })
        compositeDisposable.add(result)
    }

    private fun checkException(exception: Throwable) {
        when (exception) {
            is NoNetWorkException -> _networkErrors.value = NetworkError.NETWORK
            is BadRequestException -> _networkErrors.value = NetworkError.BAD_REQUEST
            is NotFoundException -> _networkErrors.value = NetworkError.NOT_FOUND
            is ForbiddenException -> _networkErrors.value = NetworkError.FORBIDDEN
            else -> Log.e(TAG, exception.toString())
        }
    }
}