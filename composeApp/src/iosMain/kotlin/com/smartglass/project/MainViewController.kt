package com.smartglass.project

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    // iOS에서는 Compose 시작 전에 Koin 초기화 필요
    initApp()
    App()
}