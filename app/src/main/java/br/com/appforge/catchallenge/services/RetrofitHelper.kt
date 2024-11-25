package br.com.appforge.catchallenge.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {


    companion object{
        private const val BASE_URL = "https://api.imgur.com/3/"
        private val CLIENT_ID = Secret.CLIENT_ID


        private val okHttp: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .build()

        //val movieAPI = retrofit.create(MovieAPI::class.java)

        fun <T> getApi(apiClass:Class<T>):T{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()

            return retrofit.create(apiClass)
        }
    }
}