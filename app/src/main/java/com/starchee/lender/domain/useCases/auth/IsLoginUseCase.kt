package com.starchee.lender.domain.useCases.auth

import com.starchee.lender.domain.repositories.LocalRepository
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(private val localRepository: LocalRepository) {

    operator fun invoke() = localRepository.isLogin()
}