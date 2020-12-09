package com.starchee.lender.data.dataSource.loan

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel

@Database(
    entities = [LoanModel::class, LoanConditionModel::class],
    version = 1,
    exportSchema = false
)
abstract class LoanDatabase : RoomDatabase() {
    abstract fun loanDao(): LoanDao
}