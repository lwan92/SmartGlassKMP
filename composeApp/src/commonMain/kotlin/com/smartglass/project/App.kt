package com.smartglass.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.smartglass.project.di.initKoin
import com.smartglass.project.presentation.common.rememberLoginViewModel
import com.smartglass.project.presentation.login.LoginScreen
import org.koin.compose.KoinContext

private var isKoinInitialized = false

fun initApp() {
    if (!isKoinInitialized) {
        initKoin()
        isKoinInitialized = true
    }
}

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            val viewModel = rememberLoginViewModel()
            LoginScreen(viewModel = viewModel)
        }
    }
}