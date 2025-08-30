package com.zyc.loghelper


import com.elvishew.xlog.XLog

object LogUtils {


    // 日志标签
    private const val TAG = "LogUtils"

    /**
     * verbose级别日志
     */
    fun v(message: Any?) {
        XLog.v(message)
    }

    fun v(tag: String, message: Any?) {
        XLog.tag(tag).v(message)
    }

    /**
     * debug级别日志
     */
    fun d(message: Any?) {
        XLog.d(message)
    }

    fun d(tag: String, message: Any?) {
        XLog.tag(tag).d(message)
    }

    /**
     * info级别日志
     */
    fun i(message: Any?) {
        XLog.i(message)
    }

    fun i(tag: String, message: Any?) {
        XLog.tag(tag).i(message)
    }

    /**
     * warn级别日志
     */
    fun w(message: Any?) {
        XLog.w(message)
    }

    fun w(tag: String, message: Any?) {
        XLog.tag(tag).w(message)
    }

    /**
     * error级别日志
     */
    fun e(message: Any?) {
        XLog.e(message)
    }

    fun e(tag: String, message: Any?) {
        XLog.tag(tag).e(message)
    }

    fun e(throwable: Throwable) {
        XLog.e(throwable)
    }

    fun e(tag: String, throwable: Throwable) {
        XLog.tag(tag).e(throwable)
    }

    /**
     * 打印格式化日志
     */
    fun format(priority: Int, format: String, vararg args: Any?) {
        XLog.log(priority, format, *args)
    }

    /**
     * 打印JSON格式日志
     */
    fun json(json: String) {
        XLog.json(json)
    }

    fun json(tag: String, json: String) {
        XLog.tag(tag).json(json)
    }

    /**
     * 打印XML格式日志
     */
    fun xml(xml: String) {
        XLog.xml(xml)
    }

    fun xml(tag: String, xml: String) {
        XLog.tag(tag).xml(xml)
    }
}
