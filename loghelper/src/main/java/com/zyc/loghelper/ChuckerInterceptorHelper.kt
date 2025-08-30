package com.zyc.loghelper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.Response

object ChuckerInterceptorHelper {
     fun createChuckerInterceptor(context: Context): Interceptor {
        return ChuckerInterceptor.Builder(context)
            .maxContentLength(250_000L) // 限制捕获数据大小，防止大文件导致性能问题
            .redactHeaders("Authorization", "Cookie") // 对敏感头信息（如认证token、Cookie）进行脱敏，避免泄露
            .alwaysReadResponseBody(true) // 强制读取响应体
            .build()
    }
}