package com.smartglass.project.presentation.passwordreset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartglass.project.ui.component.CommonButton
import com.smartglass.project.ui.component.CommonTextField

@Composable
fun PasswordResetScreen(
    viewModel: PasswordResetViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onResetSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is PasswordResetState.ResetSuccess -> {
                onResetSuccess()
            }
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "비밀번호 변경 요청",
            fontSize = 20.sp,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "비밀번호 변경 후 재로그인이 필요합니다.",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = "계정 보호를 위해 비밀번호를 변경해 주세요.",
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        val idleState = when (state) {
            is PasswordResetState.Idle -> state
            else -> null
        }
        
        when (state) {
            is PasswordResetState.Idle -> {
                CommonTextField(
                    value = idleState?.currentPassword ?: "",
                    onValueChange = { 
                        viewModel.handleIntent(
                            PasswordResetIntent.UpdateCurrentPassword(it)
                        )
                    },
                    placeholder = "현재 비밀번호",
                    isPassword = true,
                    showPassword = idleState?.showCurrentPassword ?: false,
                    onTogglePasswordVisibility = {
                        viewModel.handleIntent(
                            PasswordResetIntent.ToggleCurrentPasswordVisibility
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                CommonTextField(
                    value = idleState?.newPassword ?: "",
                    onValueChange = { 
                        viewModel.handleIntent(
                            PasswordResetIntent.UpdateNewPassword(it)
                        )
                    },
                    placeholder = "새 비밀번호",
                    isPassword = true,
                    showPassword = idleState?.showNewPassword ?: false,
                    onTogglePasswordVisibility = {
                        viewModel.handleIntent(
                            PasswordResetIntent.ToggleNewPasswordVisibility
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                CommonTextField(
                    value = idleState?.confirmPassword ?: "",
                    onValueChange = { 
                        viewModel.handleIntent(
                            PasswordResetIntent.UpdateConfirmPassword(it)
                        )
                    },
                    placeholder = "새 비밀번호 확인",
                    isPassword = true,
                    showPassword = idleState?.showConfirmPassword ?: false,
                    onTogglePasswordVisibility = {
                        viewModel.handleIntent(
                            PasswordResetIntent.ToggleConfirmPasswordVisibility
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "* 비밀번호 유효성 안내",
                    fontSize = 12.sp,
                    color = Color.Red
                )
                Text(
                    text = "8자 이상~20자 이내로 영문, 숫자, 특수 문자를 포함해야 합니다.",
                    fontSize = 12.sp,
                    color = Color.Red
                )
                
                idleState?.validationError?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = error,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                CommonButton(
                    text = "재설정",
                    onClick = {
                        viewModel.handleIntent(PasswordResetIntent.ResetPassword)
                    },
                    enabled = idleState?.isResetEnabled ?: false,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is PasswordResetState.Resetting -> {
                Text(
                    text = "비밀번호 변경 중...",
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is PasswordResetState.Error -> {
                val errorState = state
                Text(
                    text = errorState.message,
                    fontSize = 14.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            else -> {}
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}
