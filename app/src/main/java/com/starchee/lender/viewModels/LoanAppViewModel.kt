package com.starchee.lender.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import com.starchee.lender.domain.useCases.loan.loanApplication.CreateLoanAppUseCase
import com.starchee.lender.domain.useCases.loan.loanApplication.GetLoanConditionUseCase
import com.starchee.lender.viewModels.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoanAppViewModel @Inject constructor(
    private val createLoanAppUseCase: CreateLoanAppUseCase,
    private val getLoanConditionUseCase: GetLoanConditionUseCase,
    private val saveLocaleUseCase: SaveLocaleUseCase
) : BaseViewModel() {

    private val _loanOffers = MutableLiveData<LoanCondition>()
    private val _loanIsCreated = SingleLiveEvent<Boolean>()
    private val _networkErrors = SingleLiveEvent<NetworkError>()
    private val _inputErrors = SingleLiveEvent<InputAppError>()
    val loanOffers: LiveData<LoanCondition> = _loanOffers
    val loanIsCreated: LiveData<Boolean> = _loanIsCreated
    val networkErrors: LiveData<NetworkError> = _networkErrors
    val inputErrors: LiveData<InputAppError> = _inputErrors

    companion object {
        val TAG = LoanAppViewModel::class.java.name
    }

    fun saveLocate(locale: Locale) = saveLocaleUseCase(locale)

    fun makeLoanApplication(loanApplication: LoanApplication) {
        loanOffers.value?.let { loanCondition ->
            val result =
                createLoanAppUseCase(loanApplication, loanCondition).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _loanIsCreated.value = true
                    }, {
                        checkException(it)
                    })
            compositeDisposable.add(result)
        }
    }

    fun validateAmount(currentAmount: Int) {
        loanOffers.value?.let {
            if (currentAmount > it.maxAmount)
                _inputErrors.value = InputAppError.MAX_AMOUNT_ERROR
        }
    }

    fun loadOffer() {
        val result = getLoanConditionUseCase().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loanOffers.value = it
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
            is ForbiddenException -> _networkErrors.value = NetworkError.FORBIDDEN
            else -> Log.e(TAG, exception.toString())
        }
    }

}