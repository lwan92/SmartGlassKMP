package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import com.smartglass.project.presentation.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun rememberLoginViewModel(): LoginViewModel {
    return koinViewModel()
}
