package com.smartglass.project.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartglass.project.ui.component.CommonButton
import com.smartglass.project.ui.component.CommonCheckbox
import com.smartglass.project.ui.component.CommonTextField
import com.smartglass.project.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import smartglass.composeapp.generated.resources.Res
import smartglass.composeapp.generated.resources.logo

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(68.dp))

        // 로고 영역
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "KoELSA Logo",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(80.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 입력 필드 영역
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 아이디 입력
            CommonTextField(
                value = state.username,
                onValueChange = { viewModel.handleIntent(LoginIntent.UpdateUsername(it)) },
                placeholder = "아이디",
                modifier = Modifier.fillMaxWidth()
            )

            // 비밀번호 입력
            CommonTextField(
                value = state.password,
                onValueChange = { viewModel.handleIntent(LoginIntent.UpdatePassword(it)) },
                placeholder = "비밀번호",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 자동 로그인 체크박스
        CommonCheckbox(
            checked = state.autoLogin,
            onCheckedChange = { viewModel.handleIntent(LoginIntent.UpdateAutoLogin(it)) },
            label = "자동 로그인",
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(36.dp))

        // 확인 버튼
        CommonButton(
            text = "확인",
            onClick = { viewModel.handleIntent(LoginIntent.ClickLogin) },
            enabled = state.isLoginEnabled,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 아이디/비밀번호 찾기
        Row(
            modifier = Modifier.clickable { 
                viewModel.handleIntent(LoginIntent.ClickFindId)
            },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "아이디 찾기",
                style = AppTypography.B14px,
                color = Gray700
            )
            Text(
                text = " | ",
                style = AppTypography.B14px,
                color = Gray700
            )
            Text(
                text = "비밀번호 찾기",
                style = AppTypography.B14px,
                color = Gray700,
                modifier = Modifier.clickable {
                    viewModel.handleIntent(LoginIntent.ClickFindPassword)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 하단 브랜딩
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "NeoInspection",
                style = AppTypography.SB16px,
                color = Gray950,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Powered by DigiCAP",
                style = AppTypography.R11px,
                color = Gray950,
                textAlign = TextAlign.Center
            )
        }

        // Android Navigation Bar 공간
        Spacer(modifier = Modifier.height(48.dp))
    }
}
