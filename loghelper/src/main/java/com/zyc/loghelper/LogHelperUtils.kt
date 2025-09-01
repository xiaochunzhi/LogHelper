package com.zyc.loghelper

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.ClassicFlattener
import com.elvishew.xlog.libcat.LibCat
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.elvishew.xlog.printer.file.writer.SimpleWriter
import java.io.File

object LogHelperUtils {
    private lateinit var context: Context
    private val LOG_DIR_NAME = "logs"
    private val MAX_TIME: Long = 1000 * 60 * 60 * 24 * 7 // 7天

    fun getAppContext(): Context {
        return context
    }

    fun initialize(appContext: Context) {
        this.context = appContext.applicationContext

        initLog()
    }
    fun switchToAlternateLauncher(context: Context,isEnable: Boolean) {
        val packageManager = context.packageManager
        val componentNameDefault = ComponentName(context, "com.zyc.loghelper.LauncherAliasDefault")
        val componentNameAlternate = ComponentName(context, "com.zyc.loghelper.LauncherAliasAlternate")

        if (isEnable){
            // 启用备用别名
            packageManager.setComponentEnabledSetting(
                componentNameAlternate,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }else{
            // 禁用默认别名
            packageManager.setComponentEnabledSetting(
                componentNameDefault,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP // 可选，设置后不会立即杀死应用进程
            )
        }



    }
    private fun initLog() {

        val config = LogConfiguration.Builder()
            .tag("LogHelper") // 指定 TAG，默认为 "X-LOG"
            .enableThreadInfo() // 允许打印线程信息，默认禁止
            .enableStackTrace(2) // 允许打印深度为 2 的调用栈信息，默认禁止
            .enableBorder() // 允许打印日志边框，默认禁止

            .build()
        // 创建日志目录
        val logDir = File(LogHelperUtils.context.getExternalFilesDir(null), LogHelperUtils.LOG_DIR_NAME)
        if (!logDir.exists()) {
            logDir.mkdirs()
        }

        // 配置文件打印器
      val  filePrinter = FilePrinter.Builder(logDir.absolutePath)
            .fileNameGenerator(DateFileNameGenerator()) // 按日期生成文件名
            .backupStrategy(FileSizeBackupStrategy(1024 * 1024)) // 文件大小超过1MB时备份
            .cleanStrategy(FileLastModifiedCleanStrategy(LogHelperUtils.MAX_TIME)) // 清除7天前的日志
          .flattener(ClassicFlattener())
          .writer(object : SimpleWriter() {
          override fun appendLog(log: String) {
              // 简化版提取：只保留时间和冒号后的内容
              val colonIndex = log.indexOf(": ")
              if (colonIndex > 0) {
                  // 找到时间部分（第一个冒号前的部分通常包含时间）
                  val timePart = log.substring(0, log.indexOf(' ', log.indexOf(' ') + 1).takeIf { it > 0 } ?: colonIndex)
                  // 找到事件部分（冒号后的内容）
                  val eventPart = log.substring(colonIndex + 2)
                      .replace("═+".toRegex(), "")
                      .replace("─+".toRegex(), "")
                  super.appendLog("$timePart: $eventPart")
              } else {
                  val eventPart = log
                      .replace("═+".toRegex(), "")
                      .replace("─+".toRegex(), "")
                  // 如果没有找到标准格式，直接记录fhmnjhncdvgkrrfrgfttuuuijkkuytgyknklp;[-i9iuhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhtfegt7u890
                  super.appendLog(eventPart)
              }
          }
      }
        )
          .build()
        val androidPrinter: Printer =
            AndroidPrinter()
        // 初始化 XLog
        XLog.init( // Initialize XLog
            config,  // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
            androidPrinter,  // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
            filePrinter
        )
    }

    fun start(context: Context){
        context.startActivity(LoggerActivity.newIntent(context))
    }

    /**
     * 获取本地日志文件列表
     */
    fun getLocalLogFiles(): MutableList<File> {
        val logDir = File(context.getExternalFilesDir(null), LOG_DIR_NAME)
        return (if (logDir.exists() && logDir.isDirectory) {
            logDir.listFiles()?.toMutableList() ?: emptyList()
        } else {
            emptyList()
        }) as MutableList<File>
    }

    /**
     * 获取特定日志文件的内容
     */
    fun getLocalLogContent(fileName: String): String {
        val logFile = File(context.getExternalFilesDir(null), "$LOG_DIR_NAME/$fileName")
        return if (logFile.exists()) {
            try {
                logFile.readText()
            } catch (e: Exception) {
                "读取日志文件失败: ${e.message}"
            }
        } else {
            "日志文件不存在"
        }
    }

    /**
     * 清除所有本地日志文件
     */
    fun clearLocalLogs() {
        val logDir = File(context.getExternalFilesDir(null), LOG_DIR_NAME)
        if (logDir.exists() && logDir.isDirectory) {
            logDir.listFiles()?.forEach { file ->
                if (file.isFile) {
                    file.delete()
                }
            }
        }
    }

    /**
     * 获取日志目录路径
     */
    fun getLogDirectoryPath(): String {
        return File(context.getExternalFilesDir(null), LOG_DIR_NAME).absolutePath
    }
}
