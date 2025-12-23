package com.smartglass.project.presentation.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartglass.project.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import smartglass.composeapp.generated.resources.Res
import smartglass.composeapp.generated.resources.logo

/**
 * Intro 화면 (스플래시)
 * features_spec.md: 1. Intro 화면 참고
 */
@Composable
fun IntroScreen(
    viewModel: IntroViewModel,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 로고
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 상태 표시
            when (state) {
                is IntroState.ShowingSplash,
                is IntroState.CheckingPermissions,
                is IntroState.CheckingLogin,
                is IntroState.AutoLoginInProgress -> {
                    CircularProgressIndicator(
                        color = Primary,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val message = when (state) {
                        is IntroState.CheckingPermissions -> "권한 확인 중..."
                        is IntroState.CheckingLogin -> "로그인 상태 확인 중..."
                        is IntroState.AutoLoginInProgress -> "자동 로그인 중..."
                        else -> ""
                    }
                    
                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            style = AppTypography.M14px,
                            color = Gray700,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                is IntroState.Error -> {
                    Text(
                        text = (state as IntroState.Error).message,
                        style = AppTypography.M14px,
                        color = DestructiveText,
                        textAlign = TextAlign.Center
                    )
                }
                
                else -> {}
            }
        }
        
        // 하단 브랜딩
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NeoInspection",
                style = AppTypography.SB16px,
                color = Gray950,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Powered by DigiCAP",
                style = AppTypography.R11px,
                color = Gray950,
                textAlign = TextAlign.Center
            )
        }
    }
}
