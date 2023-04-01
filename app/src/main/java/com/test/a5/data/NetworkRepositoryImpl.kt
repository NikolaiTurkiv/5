package com.test.a5.data

import com.test.a5.data.network.NetworkApi
import com.test.a5.data.network.PhoneInfoRequest
import com.test.a5.data.network.response.SplashResponse
import com.test.a5.domain.NetworkRepository
import retrofit2.Response

class NetworkRepositoryImpl(
    private val api: NetworkApi
) : NetworkRepository {

    override suspend fun fetchPhoneStatus(
        phoneName: String,
        locale: String,
        id: String
    ): Response<SplashResponse> {
       return api.fetchPhoneStatus(PhoneInfoRequest(phoneName, locale, id))
    }
}
