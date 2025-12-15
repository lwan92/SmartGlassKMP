package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.smartglass.project.presentation.login.LoginViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
actual fun rememberLoginViewModel(): LoginViewModel {
    return remember {
        object : KoinComponent {}.get()
    }
}
