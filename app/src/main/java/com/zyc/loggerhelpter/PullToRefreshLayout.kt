package com.zyc.loggerhelpter

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
// 注意：需要添加相关依赖，例如 androidx.compose.material3:material3:1.2.0-alpha10 或更高版本
// 并使用 @OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.zyc.loggerhelpter.customheader.BallRefreshHeader
import com.zyc.loggerhelpter.refreshlayout.SwipeRefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshableScreen() {
    // 状态管理：刷新中状态应由 ViewModel 提供，此处简化表示
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()

    PullToRefreshBox(
        state = refreshState, // 传递状态
        isRefreshing = isRefreshing, // 由父组件控制的刷新状态
        onRefresh = {
            isRefreshing = true
            scope.launch {
                // 模拟网络请求或数据加载
                delay(2000)
                // 数据加载完成后，将 isRefreshing 设为 false
                isRefreshing = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            // 你的列表内容
            items(30) { index ->
                Text("Item $index", modifier = Modifier.padding(16.dp))
            }
        }
    }
}


// 自定义的下拉刷新状态
class SmartRefreshState {
    var offset by mutableStateOf(0f)
    var isRefreshing by mutableStateOf(false)
    var lastUpdated by mutableStateOf("9-5 10:47")
}

@Composable
fun rememberSmartRefreshState(): SmartRefreshState {
    return remember { SmartRefreshState() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelfareLiveScreen() {
    val refreshState = rememberSmartRefreshState()
    val list = (1..20).toList()

    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }
    Column() {

        SwipeRefreshLayout(isRefreshing = refreshing, onRefresh = { refreshing = true }, indicator = {
            BallRefreshHeader(state = it)
        }) {
            LazyColumn {
                items(list) {
                    val title = "第${it}条数据"
                    val subTitle = "这是测试的第${it}条数据"
                    LiveStreamItem(it)
                }
            }
        }
    }
}


@Composable
fun LiveStreamItem(index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 直播宣传图
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFF00), // 黄色
                            Color(0xFF00FF00)  // 绿色
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "魅力乡村 美好生活",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFA500) // 橙色
            )
            Text(
                text = "公益助农直播行动",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(top = 60.dp)
            )
        }

        // 底部按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (index % 2 == 0) {
                Text("开始", color = Color.Blue)
            } else {
                Text("已预约", color = Color.Gray)
            }
        }
    }
}
