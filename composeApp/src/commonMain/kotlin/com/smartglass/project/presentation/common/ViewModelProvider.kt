package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import com.smartglass.project.presentation.intro.IntroViewModel
import com.smartglass.project.presentation.login.LoginViewModel
import com.smartglass.project.presentation.passwordreset.PasswordResetViewModel
import com.smartglass.project.presentation.qrscan.QrScanViewModel

@Composable
expect fun rememberIntroViewModel(): IntroViewModel

@Composable
expect fun rememberLoginViewModel(): LoginViewModel

@Composable
expect fun rememberQrScanViewModel(): QrScanViewModel

@Composable
expect fun rememberPasswordResetViewModel(): PasswordResetViewModel
