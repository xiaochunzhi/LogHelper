package com.zyc.loghelper

import android.util.Log
import com.tencent.mmkv.MMKV

/**
 * 一个用于调试的工具类，用于打印 MMKV 中的所有键值对。
 * 此类应仅用于 dev 构建。
 */
object MMKVDebugger {

    fun getAllItemsFromMmkv(): Map<String, Any?> {
        val mmkv = MMKV.defaultMMKV() // 获取默认 MMKV 实例
        val keys = mmkv.allKeys() // 获取所有键
        val map = mutableMapOf<String, Any?>()

        keys?.forEach { key ->
            // 根据类型获取值，MMKV 支持多种类型
            when (val value = mmkv.decodeString(key)) {
                null -> map[key] = "null or other type"
                else -> map[key] = value
            }
            // 如果需要处理其他类型，可以添加更多判断
        }
        return map
    }
}