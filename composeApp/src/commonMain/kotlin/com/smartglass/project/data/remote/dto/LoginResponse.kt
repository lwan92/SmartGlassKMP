package com.smartglass.project.data.remote.dto

import com.smartglass.project.domain.model.LoginResult
import com.smartglass.project.domain.model.Role
import com.smartglass.project.domain.model.Token
import com.smartglass.project.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val code: String,
    val data: LoginData
)

@Serializable
data class LoginData(
    val token: TokenDto,
    val user: UserDto,
    val isPasswordReset: Boolean
)

@Serializable
data class TokenDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long
)

@Serializable
data class UserDto(
    val userId: Long,
    val loginId: String,
    val deptName: String,
    val ognzName: String,
    val positionName: String,
    val userName: String,
    val role: RoleDto
)

@Serializable
data class RoleDto(
    val roleGroupId: Long,
    val roleGroupName: String
)

// Extension functions to convert DTO to Domain model
fun TokenDto.toDomain() = Token(
    grantType = grantType,
    accessToken = accessToken,
    refreshToken = refreshToken,
    accessTokenExpiresIn = accessTokenExpiresIn,
    refreshTokenExpiresIn = refreshTokenExpiresIn
)

fun RoleDto.toDomain() = Role(
    roleGroupId = roleGroupId,
    roleGroupName = roleGroupName
)

fun UserDto.toDomain() = User(
    userId = userId,
    loginId = loginId,
    deptName = deptName,
    ognzName = ognzName,
    positionName = positionName,
    userName = userName,
    role = role.toDomain()
)

fun LoginData.toDomain() = LoginResult(
    token = token.toDomain(),
    user = user.toDomain(),
    isPasswordReset = isPasswordReset
)
