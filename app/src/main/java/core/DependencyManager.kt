package core

import com.example.lighspeed.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class DependencyManager {

    fun providesApiService(): ApiService {
        val clientBuilder = OkHttpClient.Builder()

        //Just for debug to see http call informations
        if (BuildConfig.DEBUG) {
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpInterceptor)
        }

        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl("http://0122-135-84-59-142.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)

    }

    fun providesEventViewModel() = EventViewModel(providesApiService())
}