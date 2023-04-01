package com.test.a5.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface BetManagerDao {
    @Query("SELECT * FROM bankroll_entity")
    fun getBankrolls(): Observable<List<BankrollEntity>>

    @Query("SELECT * FROM bet_entity")
    fun getAllBets(): Observable<List<BetEntity>>

    @Query("SELECT * FROM bet_entity WHERE bankrollName =:name")
    fun getNamedBets(name: String): Single<List<BetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBankroll(bankroll: BankrollEntity): Completable

    @Query("SELECT * FROM bankroll_entity WHERE bankrollName =:bankrollName")
    fun getBankroll(bankrollName: String): Single<BankrollEntity>

    @Query("UPDATE bet_entity SET isSuccess =:isSuccess, isFailure =:isFail,isWait =:isWait,actualCapital =:actualCapital WHERE id =:id")
    fun updateBet(
        id: Int,
        isSuccess: Boolean,
        isFail: Boolean,
        isWait: Boolean,
        actualCapital: Double
    ): Completable

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insertBet(bet: BetEntity): Completable

    @Query("DELETE FROM bankroll_entity")
    fun removeAllBankroll(): Completable

    @Query("DELETE FROM bet_entity")
    fun removeAllBets(): Completable

    @Query("DELETE FROM bet_entity WHERE id = :id")
    fun removeBet(id: Int): Completable

    @Query("DELETE FROM bet_entity WHERE bankrollName = :name")
    fun removeBetByName(name:String): Completable

    @Query("DELETE FROM bankroll_entity WHERE bankrollName = :name")
    fun removeBankroll(name: String): Completable
}
