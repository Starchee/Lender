package com.starchee.lender.domain.repositories

import com.starchee.lender.domain.entities.LocalUser
import com.starchee.lender.domain.entities.User
import io.reactivex.Single

interface AuthRepository {

    fun login(user: User): Single<String>
    fun registration(user: User): Single<LocalUser>

}