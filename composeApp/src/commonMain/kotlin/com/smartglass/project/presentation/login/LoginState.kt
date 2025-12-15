package com.smartglass.project.presentation.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val autoLogin: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isLoginEnabled: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}
