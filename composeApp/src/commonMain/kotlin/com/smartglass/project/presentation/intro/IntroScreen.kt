package com.smartglass.project.presentation.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import smartglass.composeapp.generated.resources.Res
import smartglass.composeapp.generated.resources.logo

/**
 * Intro 화면
 * features_spec.md 및 Figma 디자인을 기반으로 구현
 * - 스플래시 화면: 로고 이미지 표시 (2초)
 * - 권한 확인: 필수 권한 확인
 * - 자동 로그인: 토큰 및 디바이스 등록 확인 후 자동 로그인 시도
 */
@Composable
fun IntroScreen(
    viewModel: IntroViewModel,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    // 앱 시작 Intent 발생
    LaunchedEffect(Unit) {
        viewModel.handleIntent(IntroIntent.StartApp)
    }

    // 네비게이션 처리
    LaunchedEffect(state) {
        when (state) {
            is IntroState.NavigateToLogin -> {
                onNavigateToLogin()
            }
            is IntroState.NavigateToHome -> {
                onNavigateToHome()
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is IntroState.ShowingSplash,
            is IntroState.CheckingPermissions,
            is IntroState.RequestingPermissions,
            is IntroState.CheckingLogin,
            is IntroState.AutoLoginInProgress -> {
                // 스플래시 화면: 로고 표시
                // Figma 디자인에 따라 DIGICAP Digital Trust 로고 표시
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "DIGICAP Digital Trust Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(80.dp),
                    contentScale = ContentScale.Fit
                )
            }
            else -> {}
        }
    }
}
