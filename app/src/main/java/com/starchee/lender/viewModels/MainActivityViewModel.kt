package com.starchee.lender.viewModels;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starchee.lender.domain.useCases.GetSavedLocaleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val getSavedLocaleUseCase: GetSavedLocaleUseCase) :
    BaseViewModel() {

    private val _locale = MutableLiveData<String>()
    val locale: LiveData<String> = _locale

    fun getLocale(defaultLocale: String) {
        val result = getSavedLocaleUseCase(defaultLocale).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _locale.value = it
            }
        compositeDisposable.add(result)
    }
}
