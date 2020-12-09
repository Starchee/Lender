package com.starchee.lender.data.dataSource.local

import android.content.SharedPreferences
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

interface LocalDataSource {
    fun saveToken(token: String)
    fun getToken(): String
    fun isLogin(): Boolean
    fun clearToken()
    fun saveLocale(locale: String)
    fun getLocale(defaultLocale: String): Flowable<String>
}

class LocalDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LocalDataSource {

    companion object {
        const val TOKEN_KEY = "token_key"
        const val LOCALE_KEY = "locate_key"
    }

    override fun saveToken(token: String) =
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()

    override fun getToken() = sharedPreferences.getString(TOKEN_KEY, "") ?: ""

    override fun isLogin() = sharedPreferences.contains(TOKEN_KEY)

    override fun clearToken() = sharedPreferences.edit().remove(TOKEN_KEY).apply()

    override fun saveLocale(locale: String) =
        sharedPreferences.edit().putString(LOCALE_KEY, locale).apply()

    override fun getLocale(defaultLocale: String): Flowable<String> = Flowable.create({ emitter ->
        emitter.onNext(sharedPreferences.getString(LOCALE_KEY, defaultLocale) ?: defaultLocale)
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == LOCALE_KEY) {
                    emitter.onNext(
                        sharedPreferences.getString(LOCALE_KEY, defaultLocale) ?: defaultLocale
                    )
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        emitter.setCancellable {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                listener
            )
        }
    }, BackpressureStrategy.LATEST)

}