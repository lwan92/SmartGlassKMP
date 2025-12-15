package com.smartglass.project.presentation.common

import androidx.compose.runtime.Composable
import com.smartglass.project.presentation.login.LoginViewModel

@Composable
expect fun rememberLoginViewModel(): LoginViewModel
