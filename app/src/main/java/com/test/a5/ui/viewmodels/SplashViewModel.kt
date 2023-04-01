package com.test.a5.ui.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.a5.data.network.response.SplashResponse
import com.test.a5.domain.NetworkUseCase
import com.test.a5.domain.PreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.await
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val networkUseCase: NetworkUseCase,
    private val preferencesUseCase: PreferencesUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
         Log.d("ERRORR","Exception handled: ${throwable.localizedMessage}")
    }

    val id = preferencesUseCase.uniqID

    private val _response = MutableLiveData<SplashResponse>()
    val response: LiveData<SplashResponse>
        get() = _response

    fun saveId(id: String) {
        preferencesUseCase.saveId(id)
    }

    fun fetchPhoneStatus(
        phoneName: String,
        locale: String,
        id: String,
    ) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            val response = networkUseCase.fetchPhoneStatus(phoneName, locale, id).body()
            response.let {
                Log.d("SUCCESS_",it.toString())
                _response.postValue(it)
            }
        }
    }
}