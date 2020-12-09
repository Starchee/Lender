package com.starchee.lender.domain.useCases.auth

import com.starchee.lender.domain.entities.User
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(user: User): Completable =
        authRepository.login(user)
            .doOnSuccess { localRepository.saveToken(it) }
            .toCompletable()
}