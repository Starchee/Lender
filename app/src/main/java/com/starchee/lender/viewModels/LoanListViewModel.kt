package com.starchee.lender.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import com.starchee.lender.domain.useCases.loan.loanList.GetLocalLoanListUseCase
import com.starchee.lender.domain.useCases.loan.loanList.GetRemoteLoanListUseCase
import com.starchee.lender.viewModels.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanListViewModel @Inject constructor(
    private val getRemoteLoanListUseCase: GetRemoteLoanListUseCase,
    private val getLocalLoanListUseCase: GetLocalLoanListUseCase,
    private val saveLocaleUseCase: SaveLocaleUseCase
) : BaseViewModel() {

    private val _loanList = MutableLiveData<List<Loan>>()
    private val _networkErrors = SingleLiveEvent<NetworkError>()
    val loanList: LiveData<List<Loan>> = _loanList
    val networkErrors: LiveData<NetworkError> = _networkErrors

    companion object {
        val TAG = LoanListViewModel::class.java.name
    }

    fun saveLocate(locale: Locale) = saveLocaleUseCase(locale)

    fun getLoanList() {
        val result = getRemoteLoanListUseCase().subscribeOn(Schedulers.io())
            .doOnError { getLocalLoanList() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loanList.value = it
            }, {
                checkException(it)

            })
        compositeDisposable.add(result)
    }

    private fun getLocalLoanList() {
        val result = getLocalLoanListUseCase().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loanList.value = it
            }, {
                //error
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