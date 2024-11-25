package br.com.appforge.catchallenge.services

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val requestConstructor = chain.request().newBuilder()

        val request = requestConstructor.addHeader("Authorization", "Client-ID ${Secret.CLIENT_ID}").build()

        return chain.proceed(request)
    }
}