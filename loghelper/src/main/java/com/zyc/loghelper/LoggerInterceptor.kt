package com.zyc.loghelper

import android.util.Log
import com.elvishew.xlog.XLog
import okhttp3.Interceptor
import okhttp3.Response

class LoggerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 记录请求信息
        val requestLog = StringBuilder()
        requestLog.append("---------- Request ----------\n")
        requestLog.append("Method: ${request.method}\n")
        requestLog.append("URL: ${request.url}\n")
        requestLog.append("Headers: ${request.headers}\n")

        request.body?.let { body ->
            requestLog.append("Body: $body\n")
        }

        XLog.d(requestLog.toString())

        // 计算请求时间
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(request)
        val endTime = System.currentTimeMillis()

        // 记录响应信息
        val responseLog = StringBuilder()
        responseLog.append("---------- Response ----------\n")
        responseLog.append("URL: ${request.url}\n")
        responseLog.append("Status Code: ${response.code}\n")
        responseLog.append("Time: ${endTime - startTime}ms\n")
        responseLog.append("Headers: ${response.headers}\n")

        response.body?.let { body ->
            responseLog.append("Body: $body\n")
        }

        Log.d("LogHelper",responseLog.toString())

        return response
    }
}
