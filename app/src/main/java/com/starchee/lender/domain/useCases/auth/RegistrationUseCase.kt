package com.starchee.lender.domain.useCases.auth

import com.starchee.lender.domain.entities.User
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Completable
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(user: User): Completable = authRepository.registration(user)
        .flatMap { authRepository.login(user) }
        .doOnSuccess { localRepository.saveToken(it) }
        .toCompletable()
}