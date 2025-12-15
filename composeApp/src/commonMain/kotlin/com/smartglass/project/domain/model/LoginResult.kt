package com.smartglass.project.domain.model

data class LoginResult(
    val token: Token,
    val user: User,
    val isPasswordReset: Boolean
)
