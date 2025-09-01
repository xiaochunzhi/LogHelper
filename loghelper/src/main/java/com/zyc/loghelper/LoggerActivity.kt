package com.zyc.loghelper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.api.Chucker
import com.zyc.loghelper.ui.theme.LoggerHelpterTheme

class LoggerActivity : ComponentActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, LoggerActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoggerHelpterTheme {
                Scaffold(modifier = Modifier.fillMaxSize().padding(50.dp)) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        val  list = remember { mutableListOf("日志记录","网络请求日志","异常日志","mmkv记录") }
        list.forEach {
            ItemView(it) {
                when(it){
                    "日志记录" ->{
                        startActivity(LocalLogActivity.newIntent(this@LoggerActivity))
                    }
                    "网络请求日志" ->{
                        startActivity(Chucker.getLaunchIntent(this@LoggerActivity))
                    }
                    "异常日志"->{

                    }
                    "mmkv记录"->{
                        startActivity(MMKVRecordActivity.newIntent(this@LoggerActivity))
                    }
                }
            }
        }
    }
}
@Composable
fun ItemView(name: String,clickEvent:()-> Unit) {
    Text(text = name, modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clickable{
            clickEvent()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoggerHelpterTheme {
        Greeting("Android")
    }
}
}
