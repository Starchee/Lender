package com.starchee.lender.data.repositories

import com.starchee.lender.data.dataSource.local.LocalDataSource
import com.starchee.lender.domain.entities.Locale
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LocalRepository {

    override fun saveToken(token: String) = localDataSource.saveToken(token)
    override fun getToken() = localDataSource.getToken()
    override fun isLogin() = localDataSource.isLogin()
    override fun clearToken() = localDataSource.clearToken()
    override fun saveLocale(locale: Locale) = localDataSource.saveLocale(locale.toString().toLowerCase())
    override fun getLocale(defaultLocale: String): Flowable<String> =
        localDataSource.getLocale(defaultLocale)

}