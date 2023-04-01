package com.test.a5.data.network

  import com.test.a5.data.network.response.SplashResponse
 import io.reactivex.rxjava3.core.Single
  import retrofit2.Call
  import retrofit2.Response
  import retrofit2.http.Body
 import retrofit2.http.POST

interface NetworkApi {

    @POST("splash.php")
   suspend fun fetchPhoneStatus(
       @Body request: PhoneInfoRequest
    ): Response<SplashResponse>

}
