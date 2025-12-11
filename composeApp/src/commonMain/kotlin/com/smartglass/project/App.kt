package com.smartglass.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.smartglass.project.di.initKoin
import com.smartglass.project.presentation.main.MainScreen

private var isKoinInitialized = false

fun initApp() {
    if (!isKoinInitialized) {
        initKoin()
        isKoinInitialized = true
    }
}

@Composable
fun App() {
    LaunchedEffect(Unit) {
        initApp()
    }
    MainScreen()
}