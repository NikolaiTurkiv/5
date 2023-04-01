package com.test.a5.domain

 import com.test.a5.data.network.response.SplashResponse
 import retrofit2.Response

interface NetworkRepository {

   suspend fun fetchPhoneStatus(
        phoneName: String,
        locale: String,
        id: String,
    ): Response<SplashResponse>
}