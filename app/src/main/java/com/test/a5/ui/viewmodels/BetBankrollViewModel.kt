package com.test.a5.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.a5.data.db.entities.BankrollEntity
import com.test.a5.data.db.entities.BetEntity
import com.test.a5.domain.DatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BetBankrollViewModel @Inject constructor(
    private val databaseUseCase: DatabaseUseCase
) : ViewModel() {

    private val _betsLd = MutableLiveData<List<BetEntity>>()
    val betsLd: LiveData<List<BetEntity>>
        get() = _betsLd

    private val _bankrollListLd = MutableLiveData<List<BankrollEntity>>()
    val bankrollLIstLd: LiveData<List<BankrollEntity>>
        get() = _bankrollListLd

    private val _bankrollLd = MutableLiveData<BankrollEntity>()
    val bankrollLd: LiveData<BankrollEntity>
        get() = _bankrollLd

    private val _bankrollsVisibility = MutableLiveData<Boolean>()
    val bankrollsVisibility: LiveData<Boolean>
        get() = _bankrollsVisibility


    private val _betVisibility = MutableLiveData<Boolean>()
    val betVisibility: LiveData<Boolean>
        get() = _betVisibility

    private val _isBankrollRemoved = MutableLiveData<Boolean>()
    val isBankrollRemoved: LiveData<Boolean>
        get() = _isBankrollRemoved

    private val _isRefreshed = MutableLiveData<Boolean>()
    val isRefreshed: LiveData<Boolean>
        get() = _isRefreshed

    fun getBankroll(name: String){
        databaseUseCase.getBankrolls(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _bankrollLd.postValue(it)
                Log.d("GET_BANKROLL", "SUCCESS")
            }, {
                Log.d("GET_BANKROLL", it.message.toString())
            })
    }

    fun updateBet(betEntity: BetEntity,name: String){
        databaseUseCase.updateBet(betEntity)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("UPDATE_BET", "SUCCESS")
                getNamedBets(name)
            }, {
                Log.d("UPDATE_BET", it.message.toString())
            })
    }

    fun saveBet(
        name: String,
        odd: Double,
        amount: Double,
        bankrollName: String,
    ) {
        databaseUseCase.saveBet(
            BetEntity(
                name = name,
                odd = odd,
                amount = amount,
                bankrollName = bankrollName
            )
        )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("SAVE_BET", "SUCCESS")
                getNamedBets(bankrollName)
            }, {
                Log.d("SAVE_BET", it.message.toString())
            })
    }


    fun getBankrolls() {
        databaseUseCase.getBankrolls
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                val list = mutableListOf<BankrollEntity>(BankrollEntity("Choose bankroll", 0.0,0.0))
                list.addAll(it)
                _bankrollListLd.postValue(list)
                if(!it.isNullOrEmpty()){
                    _bankrollsVisibility.postValue(true)
                }else{
                    _bankrollsVisibility.postValue(false)
                }
            }, {
                Log.d("GET_BANKROLLS", it.message.toString())
            })
    }

    fun getNamedBets(name: String){
        databaseUseCase.getNamedBets(name)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                _isRefreshed.postValue(true)
                if (!it.isNullOrEmpty())
                       _betsLd.postValue(it)
                else
                    _betsLd.postValue(listOf())
                Log.d("GET_BETS_NAMED", it.toString())
            },{
                _isRefreshed.postValue(false)
                Log.d("GET_BETS_NAMED", it.message.toString())
            })
    }


    fun getBets() {
        databaseUseCase.getBets
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.d("GET_BETS", it.toString())
                if(!it.isNullOrEmpty()){
                    _betVisibility.postValue(true)
                }else{
                    _betVisibility.postValue(false)
                }
            }, {
                Log.d("GET_BETS", it.message.toString())
            })
    }


    fun saveBankroll(name: String, capital: Double,actualCapital: Double =0.0) {
        databaseUseCase.saveBankroll(BankrollEntity(name, capital,actualCapital))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("SAVE_BANKROLL", "SUCCESS")
            }, {
                Log.d("SAVE_BANKROLL", it.message.toString())
            })
    }

    fun removeBet(id:Int,name: String){
        databaseUseCase.removeBet(id)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getNamedBets(name)
                Log.d("REMOVE_BET", "SUCCESS")
            }, {
                Log.d("REMOVE_BET", it.message.toString())
            })
    }

    fun removeBetByName(name:String){
        databaseUseCase.removeBankroll(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getBankrolls()
                Log.d("removeBetByName", "SUCCESS")
            }, {
                Log.d("removeBetByName", it.message.toString())
            })
    }

     fun removeBankroll(name: String){
         databaseUseCase.removeBetByName(name)
             .observeOn(Schedulers.io())
             .subscribeOn(Schedulers.io())
             .subscribe({
                 getBankrolls()
                 _isBankrollRemoved.postValue(true)
                 Log.d("removeBankroll", "SUCCESS")
             }, {
                 _isBankrollRemoved.postValue(false)
                 Log.d("removeBankroll", it.message.toString())
             })
     }

}
