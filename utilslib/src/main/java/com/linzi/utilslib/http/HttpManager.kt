package com.linzi.utilslib.http

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object HttpManager {
    var TIMEOUT:Long=15
    private val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
    private var moduleHttp=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient.build())
    fun addInterceptor(interceptor: Interceptor){
        httpClient.addInterceptor(interceptor)
        moduleHttp.client(httpClient.build())
    }
    fun <T>moduleHttp(t:Class<T>):T{
        moduleHttp.baseUrl(HttpUrls.ROOT_URL)
        return moduleHttp.build().create(t)
    }

}