package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.smartglass.project.presentation.intro.IntroViewModel
import com.smartglass.project.presentation.login.LoginViewModel
import com.smartglass.project.presentation.passwordreset.PasswordResetViewModel
import com.smartglass.project.presentation.qrscan.QrScanViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
actual fun rememberIntroViewModel(): IntroViewModel {
    return remember {
        object : KoinComponent {}.get()
    }
}

@Composable
actual fun rememberLoginViewModel(): LoginViewModel {
    return remember {
        object : KoinComponent {}.get()
    }
}

@Composable
actual fun rememberQrScanViewModel(): QrScanViewModel {
    return remember {
        object : KoinComponent {}.get()
    }
}

@Composable
actual fun rememberPasswordResetViewModel(): PasswordResetViewModel {
    return remember {
        object : KoinComponent {}.get()
    }
}
