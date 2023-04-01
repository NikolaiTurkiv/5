package com.test.a5.domain

 import com.test.a5.data.network.response.SplashResponse
 import retrofit2.Call
 import retrofit2.Response

class NetworkUseCase(
    private val networkRepository: NetworkRepository
) {

    suspend fun fetchPhoneStatus(
        phoneName: String,
        locale: String,
        id: String,
    ): Response<SplashResponse> {
        return networkRepository.fetchPhoneStatus(phoneName, locale, id)
    }
}