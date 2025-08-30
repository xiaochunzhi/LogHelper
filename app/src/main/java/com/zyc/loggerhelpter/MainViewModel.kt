package com.zyc.loggerhelpter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyc.loghelper.LogUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _userData = MutableStateFlow<FoxResponse?>(null)
    val userData: StateFlow<FoxResponse?> = _userData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchUser() {
        viewModelScope.launch {
            NetworkHelper.getUser()
            Log.e("测试一下","原生log测试")
            LogUtils.e("xlog测试一下","xlog测试")
        }
    }

}