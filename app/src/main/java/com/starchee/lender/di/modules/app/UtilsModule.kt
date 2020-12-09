package com.starchee.lender.di.modules.app

import com.starchee.lender.data.utils.DateUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideDateUtils() = DateUtils()
}