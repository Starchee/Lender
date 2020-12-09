package com.starchee.lender.di.modules.local

import android.content.SharedPreferences
import com.starchee.lender.data.dataSource.local.LocalDataSource
import com.starchee.lender.data.dataSource.local.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModuleImpl {

    @Singleton
    @Provides
    fun provideLocalDataSourceImpl(sharedPreferences: SharedPreferences): LocalDataSource =
        LocalDataSourceImpl(sharedPreferences)
}