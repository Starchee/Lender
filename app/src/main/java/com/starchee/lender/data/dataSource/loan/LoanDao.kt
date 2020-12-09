package com.starchee.lender.data.dataSource.loan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starchee.lender.data.models.LoanModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoanList(loanList: List<LoanModel>)

    @Query("DELETE FROM loans ")
    fun deleteAllLoans(): Completable

    @Query("SELECT * FROM loans")
    fun getLoanList(): Single<List<LoanModel>>

    @Query("SELECT * from loans WHERE id LIKE :id")
    fun getLoanById(id: Int): Single<LoanModel>
}