package com.zyc.loggerhelpter

import com.zyc.loghelper.ChuckerInterceptorHelper
import com.zyc.loghelper.LogHelperUtils
import com.zyc.loghelper.LoggerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://randomfox.ca/" // 替换为你的基础URL

    // 创建 OkHttpClient
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
            .readTimeout(30, TimeUnit.SECONDS)    // 读取超时
            .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时
            .addInterceptor(LoggerInterceptor())
            .addInterceptor(ChuckerInterceptorHelper.createChuckerInterceptor(LogHelperUtils.getAppContext()))
            .addInterceptor { chain ->
                // 添加公共请求头
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    // .header("Authorization", "Bearer your_token") // 如需认证
                    .method(originalRequest.method, originalRequest.body)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    // 创建 Retrofit 实例
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 获取 Service 实例
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}