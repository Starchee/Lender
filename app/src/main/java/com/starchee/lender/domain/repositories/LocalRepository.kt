package com.starchee.lender.domain.repositories

import com.starchee.lender.domain.entities.Locale
import io.reactivex.Flowable

interface LocalRepository {
    fun saveToken(token: String)
    fun getToken(): String
    fun isLogin(): Boolean
    fun clearToken()
    fun saveLocale(locale: Locale)
    fun getLocale(defaultLocale: String): Flowable<String>
}