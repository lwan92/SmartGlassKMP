package com.smartglass.project.presentation.login

sealed class LoginIntent {
    data class UpdateUsername(val username: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
    data class UpdateAutoLogin(val autoLogin: Boolean) : LoginIntent()
    object ClickLogin : LoginIntent()
    object ClickFindId : LoginIntent()
    object ClickFindPassword : LoginIntent()
}
