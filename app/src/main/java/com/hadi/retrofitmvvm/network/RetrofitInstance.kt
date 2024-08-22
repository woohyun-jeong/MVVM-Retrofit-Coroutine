package com.hadi.retrofitmvvm.network

import com.hadi.retrofitmvvm.app.MyApplication
import com.hadi.retrofitmvvm.util.Constants.BASE_URL
import com.hadi.retrofitmvvm.util.Constants.BASE_URL_2
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RetrofitInstance {
    companion object {

        private val retrofitLogin by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val retrofitPicsum by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            val cacheDirectory = File(MyApplication.instance.applicationContext.cacheDir, "cache")
            val cache = Cache(cacheDirectory, cacheSize.toLong())
            val client = OkHttpClient.Builder()
                .protocols(listOf(Protocol.QUIC, Protocol.HTTP_2, Protocol.HTTP_1_1))
                .cache(cache)
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }


        val loginApi by lazy {
            retrofitLogin.create(API::class.java)
        }

        val picsumApi by lazy {
            retrofitPicsum.create(API::class.java)
        }

        val ImageApi by lazy {
            retrofitPicsum.create(API::class.java)
        }
    }
}