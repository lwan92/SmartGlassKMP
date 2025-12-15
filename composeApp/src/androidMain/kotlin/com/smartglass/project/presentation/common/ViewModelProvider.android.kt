package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import com.smartglass.project.presentation.intro.IntroViewModel
import com.smartglass.project.presentation.login.LoginViewModel
import com.smartglass.project.presentation.passwordreset.PasswordResetViewModel
import com.smartglass.project.presentation.qrscan.QrScanViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun rememberIntroViewModel(): IntroViewModel {
    return koinViewModel()
}

@Composable
actual fun rememberLoginViewModel(): LoginViewModel {
    return koinViewModel()
}

@Composable
actual fun rememberQrScanViewModel(): QrScanViewModel {
    return koinViewModel()
}

@Composable
actual fun rememberPasswordResetViewModel(): PasswordResetViewModel {
    return koinViewModel()
}
