package com.starchee.lender.domain.useCases

import com.starchee.lender.domain.entities.Locale
import com.starchee.lender.domain.repositories.LocalRepository
import javax.inject.Inject

class SaveLocaleUseCase @Inject constructor(private val locateRepository: LocalRepository) {

    operator fun invoke(locale: Locale) = locateRepository.saveLocale(locale)
}