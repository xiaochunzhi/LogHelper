package com.zyc.loghelper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zyc.loghelper.ui.theme.LoggerHelpterTheme
import java.io.File

class LocalLogActivity : ComponentActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LocalLogActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoggerHelpterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){ innerPadding ->

                    val logList = remember { mutableStateListOf<File>().apply { addAll(LogHelperUtils.getLocalLogFiles()) } }
                    Column {
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 50.dp)) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,  // 使用 Material 内置的清除图标
                                contentDescription = "Clear",      // 无障碍描述
                                modifier = Modifier.padding(10.dp) .clickable{
                                    finish()
                                }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "日志", modifier = Modifier.padding(10.dp))
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Delete,  // 使用 Material 内置的清除图标
                                contentDescription = "Clear",      // 无障碍描述
                                modifier = Modifier.padding(10.dp) .clickable{
                                    try {
                                        LogHelperUtils.clearLocalLogs()
                                    }catch (e: Exception){

                                    }finally {
                                        logList.clear()
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(contentPadding = PaddingValues(10.dp), modifier = Modifier.padding(horizontal = 12.dp)) {
                            logList?.forEach { file ->
                                item {
                                    LogUtils.e(LogHelperUtils.getLocalLogContent(file.name))
                                    LogItemView(LogHelperUtils.getLocalLogContent(file.name))
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun LogItemView(log: String) {

        val eventPart = log
            .replace("═+".toRegex(), "")
            .replace("─+".toRegex(), "")

        Column(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = eventPart,
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color(0xff990000)
            )
        }
    }
}