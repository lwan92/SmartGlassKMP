package com.smartglass.project.data.network

import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.data.remote.api.AuthApi
import com.smartglass.project.data.remote.dto.TokenRefreshRequest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 토큰 갱신 코디네이터
 * features_spec.md 4.1.2 참조
 * 
 * 중복 갱신을 방지하고 동기화된 토큰 갱신을 제공합니다.
 */
class TokenRefreshCoordinator(
    private val authApi: AuthApi,
    private val preferencesManager: PreferencesManager
) {
    private val mutex = Mutex()
    private var isRefreshing = false
    private var refreshResult: Result<String>? = null
    
    /**
     * 새로운 Access Token을 가져옵니다.
     * 이미 갱신 중이면 진행 중인 갱신의 결과를 반환합니다.
     * 
     * @return Result<String> 성공 시 새로운 Access Token
     */
    suspend fun awaitFreshAccessToken(): Result<String> {
        mutex.withLock {
            // 이미 갱신 중이면 진행 중인 갱신의 결과를 반환
            if (isRefreshing && refreshResult != null) {
                return refreshResult!!
            }
            
            // 갱신 시작
            isRefreshing = true
            refreshResult = null
            
            try {
                val refreshToken = preferencesManager.getRefreshToken()
                if (refreshToken == null) {
                    refreshResult = Result.failure(Exception("Refresh token not found"))
                    return refreshResult!!
                }
                
                val request = TokenRefreshRequest(
                    refreshToken = refreshToken,
                    bypassRefreshExpiry = true
                )
                
                val response = authApi.refreshToken(request)
                
                if (response.success && response.data != null) {
                    val tokenDto = response.data.token
                    if (tokenDto?.accessToken != null && tokenDto.refreshToken != null) {
                        // 새로운 토큰 저장
                        preferencesManager.saveAccessToken(tokenDto.accessToken)
                        preferencesManager.saveRefreshToken(tokenDto.refreshToken)
                        
                        refreshResult = Result.success(tokenDto.accessToken)
                        return refreshResult!!
                    } else {
                        refreshResult = Result.failure(Exception("Token data is null"))
                        return refreshResult!!
                    }
                } else {
                    val errorMessage = response.message ?: "Token refresh failed (code: ${response.code})"
                    refreshResult = Result.failure(Exception(errorMessage))
                    return refreshResult!!
                }
            } catch (e: Exception) {
                refreshResult = Result.failure(e)
                return refreshResult!!
            } finally {
                isRefreshing = false
            }
        }
    }
    
    /**
     * 현재 갱신 중인지 확인합니다.
     */
    suspend fun isRefreshing(): Boolean {
        return mutex.withLock { isRefreshing }
    }
    
    /**
     * 갱신 상태를 초기화합니다 (테스트용).
     */
    suspend fun reset() {
        mutex.withLock {
            isRefreshing = false
            refreshResult = null
        }
    }
}

