package com.smartglass.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.smartglass.project.di.initKoin
import com.smartglass.project.presentation.common.rememberIntroViewModel
import com.smartglass.project.presentation.common.rememberLoginViewModel
import com.smartglass.project.presentation.common.rememberPasswordResetViewModel
import com.smartglass.project.presentation.common.rememberQrScanViewModel
import com.smartglass.project.presentation.home.HomeScreen
import com.smartglass.project.presentation.intro.IntroScreen
import com.smartglass.project.presentation.intro.IntroState
import com.smartglass.project.presentation.login.LoginScreen
import com.smartglass.project.presentation.passwordreset.PasswordResetScreen
import com.smartglass.project.presentation.qrscan.QrScanScreen
import org.koin.compose.KoinContext

private var isKoinInitialized = false

fun initApp() {
    if (!isKoinInitialized) {
        initKoin()
        isKoinInitialized = true
    }
}

/**
 * 앱의 네비게이션을 관리하는 sealed class
 */
sealed class Screen {
    object Intro : Screen()
    object Login : Screen()
    object Home : Screen()
    object PasswordReset : Screen()
    object QrScan : Screen()
}

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Intro) }
            
            when (currentScreen) {
                is Screen.Intro -> {
                    val introViewModel = rememberIntroViewModel()
                    IntroScreen(
                        viewModel = introViewModel,
                        onNavigateToLogin = {
                            currentScreen = Screen.Login
                        },
                        onNavigateToHome = {
                            currentScreen = Screen.Home
                        }
                    )
                }
                is Screen.Login -> {
                    val loginViewModel = rememberLoginViewModel()
                    LoginScreen(viewModel = loginViewModel)
                }
                is Screen.Home -> {
                    HomeScreen()
                }
                is Screen.PasswordReset -> {
                    val passwordResetViewModel = rememberPasswordResetViewModel()
                    PasswordResetScreen(
                        viewModel = passwordResetViewModel,
                        onClose = {
                            currentScreen = Screen.Login
                        },
                        onResetSuccess = {
                            // 비밀번호 재설정 성공 후 로그인 화면으로 이동
                            currentScreen = Screen.Login
                        }
                    )
                }
                is Screen.QrScan -> {
                    val qrScanViewModel = rememberQrScanViewModel()
                    QrScanScreen(
                        viewModel = qrScanViewModel,
                        onClose = {
                            currentScreen = Screen.Login
                        },
                        onDeviceRegistered = { appId ->
                            // 디바이스 등록 완료 후 로그인 화면으로 돌아가기
                            currentScreen = Screen.Login
                        }
                    )
                }
            }
        }
    }
}
