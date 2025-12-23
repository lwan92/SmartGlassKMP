package com.smartglass.project.data.remote.dto

import com.smartglass.project.domain.model.LoginResult
import com.smartglass.project.domain.model.Role
import com.smartglass.project.domain.model.Token
import com.smartglass.project.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 로그인 응답
 * api_spec.md 2.1 참조
 * ApiResponse<LoginData> 구조를 따름
 */
@Serializable
data class LoginResponse(
    val success: Boolean,
    val code: String,
    val errorKey: String? = null,
    val message: String? = null,
    val status: Int? = null,
    val data: LoginData? = null
)

@Serializable
data class LoginData(
    val token: TokenDto? = null,
    val user: UserDto? = null,
    @SerialName("isPasswordReset")
    val isPasswordReset: Boolean? = null
)

@Serializable
data class TokenDto(
    val grantType: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val accessTokenExpiresIn: Long? = null,
    val refreshTokenExpiresIn: Long? = null
)

@Serializable
data class UserDto(
    val userNo: Int? = null,
    val userId: String? = null,
    val loginId: String? = null,
    val deptName: String? = null,
    val ognzName: String? = null,
    val positionName: String? = null,
    val userName: String? = null,
    val role: RoleDto? = null
)

@Serializable
data class RoleDto(
    val roleGroupId: Int? = null,
    val roleGroupName: String? = null
)

// Extension functions to convert DTO to Domain model
fun TokenDto.toDomain(): Token {
    requireNotNull(accessToken) { "accessToken is required" }
    requireNotNull(refreshToken) { "refreshToken is required" }
    return Token(
        grantType = grantType ?: "Bearer",
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessTokenExpiresIn = accessTokenExpiresIn ?: 0L,
        refreshTokenExpiresIn = refreshTokenExpiresIn ?: 0L
    )
}

fun RoleDto.toDomain(): Role {
    requireNotNull(roleGroupId) { "roleGroupId is required" }
    requireNotNull(roleGroupName) { "roleGroupName is required" }
    return Role(
        roleGroupId = roleGroupId.toLong(),
        roleGroupName = roleGroupName
    )
}

fun UserDto.toDomain(): User {
    requireNotNull(userId) { "userId is required" }
    requireNotNull(loginId) { "loginId is required" }
    requireNotNull(userName) { "userName is required" }
    requireNotNull(role) { "role is required" }
    return User(
        userNo = userNo,
        userId = userId,
        loginId = loginId,
        deptName = deptName ?: "",
        ognzName = ognzName ?: "",
        positionName = positionName ?: "",
        userName = userName,
        role = role.toDomain()
    )
}

fun LoginData.toDomain(): LoginResult {
    requireNotNull(token) { "token is required" }
    requireNotNull(user) { "user is required" }
    return LoginResult(
        token = token.toDomain(),
        user = user.toDomain(),
        isPasswordReset = isPasswordReset ?: false
    )
}
