package com.zyc.loggerhelpter

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("floof/") // 端点路径，基础URL为 https://randomfox.ca/
    suspend fun getRandomFox(): FoxResponse // suspend 函数用于协程
}