package com.starchee.lender.di.modules.app

import android.app.Application
import androidx.room.Room
import com.starchee.lender.data.dataSource.loan.LoanDao
import com.starchee.lender.data.dataSource.loan.LoanDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoanDatabaseModule {

    companion object {
        private const val DATABASE_NAME = "loans_database"
    }

    @Singleton
    @Provides
    fun provideRatesDatabase(application: Application): LoanDao {
        return Room.databaseBuilder(application, LoanDatabase::class.java, DATABASE_NAME).build()
            .loanDao()
    }
}