package core

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    @Headers("Content-Type: application/json")
    suspend fun registerEvent(@Body event : EventEntity?) : Response<EventEntity.Result>
}