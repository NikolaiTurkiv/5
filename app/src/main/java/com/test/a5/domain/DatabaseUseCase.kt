package com.test.a5.domain

 import com.test.a5.data.db.entities.BankrollEntity
 import com.test.a5.data.db.entities.BetEntity
 import io.reactivex.rxjava3.core.Completable
 import io.reactivex.rxjava3.core.Observable
 import io.reactivex.rxjava3.core.Single

class DatabaseUseCase(
    private val database: DatabaseRepository
) {
    val getBankrolls = database.getBankrolls()
    val getBets = database.getAllBets()

    fun saveBet(betEntity: BetEntity): Completable{
        return database.insertBet(betEntity)
    }

    fun getBankrolls(name:String): Single<BankrollEntity>{
        return database.getBankroll(name)
    }

    fun getNamedBets(name:String): Single<List<BetEntity>> {
        return database.getNamedBets(name)
    }

    fun saveBankroll(bankrollEntity: BankrollEntity): Completable{
        return  database.insertBankroll(bankrollEntity)
    }

    fun removeBet(id:Int): Completable{
        return database.removeBet(id)
    }

    fun updateBet(betEntity: BetEntity): Completable{
        return database.updateBet(betEntity)
    }

    fun removeBetByName(name: String): Completable {
        return database.removeBetByName(name)
    }

    fun removeBankroll(name: String): Completable {
        return database.removeBankroll(name)
    }

}
