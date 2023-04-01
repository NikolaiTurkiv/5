package com.test.a5.domain

import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface DatabaseRepository {

     fun getBankrolls(): Observable<List<BankrollEntity>>

     fun getAllBets(): Observable<List<BetEntity>>

     fun getNamedBets(name:String):Single<List<BetEntity>>

     fun getBankroll(banckrollName:String):Single<BankrollEntity>

     fun insertBankroll(bankroll: BankrollEntity): Completable

     fun updateBet(bet: BetEntity): Completable

     fun insertBet(bet: BetEntity): Completable

     fun removeAllBankroll():Completable

     fun removeAllBets(): Completable

     fun removeBet(id: Int): Completable

     fun removeBetByName(name:String): Completable

      fun removeBankroll(name: String): Completable
}
