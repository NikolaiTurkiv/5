package com.test.a5.data

import com.test.a5.data.db.dao.BetManagerDao
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import com.test.a5.domain.DatabaseRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class DatabaseRepositoryImpl(
    private val db : BetManagerDao
): DatabaseRepository {

    override fun getBankrolls(): Observable<List<BankrollEntity>> {
        return db.getBankrolls()
    }

    override fun getAllBets(): Observable<List<BetEntity>> {
       return db.getAllBets()
    }

    override fun getNamedBets(name: String): Single<List<BetEntity>> {
        return db.getNamedBets(name)
    }

    override fun getBankroll(banckrollName: String): Single<BankrollEntity> {
         return db.getBankroll(bankrollName = banckrollName)
    }

    override fun insertBankroll(bankroll: BankrollEntity): Completable {
        return db.insertBankroll(bankroll)
    }

    override fun updateBet(bet: BetEntity): Completable {
        return db.updateBet(
            id = bet.id,
            isSuccess = bet.isSuccess,
            isFail = bet.isFailure,
            isWait = bet.isWait,
            actualCapital = bet.actualCapital
        )
    }

    override fun insertBet(bet: BetEntity): Completable {
        return db.insertBet(bet)
    }

    override fun removeAllBankroll(): Completable {
        return db.removeAllBankroll()
    }

    override fun removeAllBets(): Completable {
        return db.removeAllBets()
    }

    override fun removeBet(id: Int): Completable {
        return db.removeBet(id)
    }

    override fun removeBetByName(name: String): Completable {
        return db.removeBetByName(name)
     }

    override fun removeBankroll(name: String): Completable {
        return db.removeBankroll(name)
     }

}
