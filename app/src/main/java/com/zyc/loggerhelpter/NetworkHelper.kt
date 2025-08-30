package com.zyc.loggerhelpter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NetworkHelper {
    private val apiService: ApiService by lazy {
        RetrofitClient.createService(ApiService::class.java)
    }

    // 通用网络请求封装
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            withContext(Dispatchers.IO) {
                Result.success(apiCall.invoke())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Result.failure(e)
            }
        }
    }

    // 具体接口调用方法
    suspend fun getUser(): Result<FoxResponse> {
        return safeApiCall { apiService.getRandomFox() }
    }

}