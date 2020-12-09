package com.starchee.lender.domain.useCases

import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetSavedLocaleUseCase @Inject constructor(private val localRepository: LocalRepository) {

    operator fun invoke(defaultLocale: String): Flowable<String> =
        localRepository.getLocale(defaultLocale)
}