package ru.iskhakoff.hackaton.data.remote.helper

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.iskhakoff.hackaton.data.remote.service.ApiService
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    companion object {
        private const val BASE_URL = "http://dme.kwel.ru/api/"

        private fun getOkHttpInstance() : OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build()
        }

        private fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getService() : ApiService = getRetrofitInstance().create(ApiService::class.java)
    }

}