package com.zyc.loggerhelpter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.zyc.loggerhelpter.ui.theme.LoggerHelpterTheme
import com.zyc.loghelper.LogHelperUtils

class LaunchActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoggerHelpterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        viewModel.fetchUser()
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .clickable {
                LogHelperUtils.start(context = this@LaunchActivity)
            }) {

            Text("日志记录")
        }
    }

    @Composable
    fun ItemView(name: String, clickEvent: () -> Unit) {
        Text(
            text = name, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
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