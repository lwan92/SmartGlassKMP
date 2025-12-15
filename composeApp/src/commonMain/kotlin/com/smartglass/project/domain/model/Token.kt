package com.smartglass.project.domain.model

data class Token(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long
)
