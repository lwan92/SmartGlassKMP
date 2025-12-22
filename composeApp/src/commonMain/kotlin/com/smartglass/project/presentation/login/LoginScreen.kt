package com.smartglass.project.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.smartglass.project.ui.component.*
import com.smartglass.project.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import smartglass.composeapp.generated.resources.Res
import smartglass.composeapp.generated.resources.logo

/**
 * 로그인 화면
 * Figma: https://www.figma.com/design/diRXHJDeWdqsBzI1qcA8I4/SmartGlass-Design?node-id=7109-46090
 * features_spec.md: 2. 로그인 화면 참고
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToPasswordReset: () -> Unit = {},
    onNavigateToQrScan: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    
    // 네비게이션 처리
    LaunchedEffect(state) {
        when (state) {
            is LoginState.NavigateToHome -> {
                onNavigateToHome()
            }
            is LoginState.NavigateToPasswordReset -> {
                onNavigateToPasswordReset()
            }
            is LoginState.NavigateToQrScan -> {
                onNavigateToQrScan()
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(68.dp))

            // 로고 영역 (Figma 기준)
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(80.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Idle 상태에서만 입력 필드 표시
            when (val currentState = state) {
                is LoginState.Idle -> {
                    // 아이디 입력
                    CommonTextField(
                        value = currentState.username,
                        onValueChange = { 
                            viewModel.handleIntent(LoginIntent.UpdateUsername(it)) 
                        },
                        placeholder = "아이디",
                        modifier = Modifier.fillMaxWidth(),
                        onFocusChange = { isFocused ->
                            if (isFocused) {
                                viewModel.handleIntent(LoginIntent.OnFieldFocused)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 비밀번호 입력
                    CommonTextField(
                        value = currentState.password,
                        onValueChange = { 
                            viewModel.handleIntent(LoginIntent.UpdatePassword(it)) 
                        },
                        placeholder = "비밀번호",
                        modifier = Modifier.fillMaxWidth(),
                        isPassword = true,
                        showPassword = currentState.showPassword,
                        onTogglePasswordVisibility = {
                            viewModel.handleIntent(LoginIntent.TogglePasswordVisibility)
                        },
                        onFocusChange = { isFocused ->
                            if (isFocused) {
                                viewModel.handleIntent(LoginIntent.OnFieldFocused)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 자동 로그인 체크박스
                    CommonCheckbox(
                        checked = currentState.autoLogin,
                        onCheckedChange = { 
                            viewModel.handleIntent(LoginIntent.UpdateAutoLogin(it)) 
                        },
                        label = "자동 로그인",
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    // 확인 버튼
                    CommonButton(
                        text = "확인",
                        onClick = { 
                            viewModel.handleIntent(LoginIntent.ClickLogin) 
                        },
                        enabled = currentState.isLoginEnabled,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 아이디/비밀번호 찾기 (Figma 기준 - QR 버튼 제거)
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
                }
                
                is LoginState.LoginInProgress, 
                is LoginState.QrLoginInProgress,
                is LoginState.DeviceRegistrationInProgress -> {
                    // 로딩 표시
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
                
                is LoginState.LoginFailure -> {
                    // 에러 메시지 표시
                    Text(
                        text = currentState.message ?: "로그인에 실패했습니다.",
                        style = AppTypography.M14px,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                
                else -> {
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 하단 브랜딩 (Figma 기준)
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
        
        // 팝업 표시
        when (state) {
            is LoginState.DeviceRegistrationRequired -> {
                DeviceRegistrationPopup(
                    onQrScanClick = {
                        viewModel.handleIntent(LoginIntent.ClickDeviceRegistration)
                    },
                    onDismiss = {
                        viewModel.handleIntent(LoginIntent.DeviceRegistrationCancelled)
                    }
                )
            }
            
            is LoginState.DuplicateLogin -> {
                DuplicateLoginPopup(
                    onConfirm = {
                        viewModel.handleIntent(LoginIntent.ConfirmDuplicateLogin)
                    },
                    onCancel = {
                        viewModel.handleIntent(LoginIntent.CancelDuplicateLogin)
                    }
                )
            }
            
            is LoginState.ShowFindAccountPopup -> {
                FindAccountPopup(
                    onConfirm = {
                        viewModel.handleIntent(LoginIntent.CloseFindAccountPopup)
                    }
                )
            }
            
            else -> {}
        }
    }
}
