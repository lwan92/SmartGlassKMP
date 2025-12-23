package com.smartglass.project.data.network

import com.smartglass.project.data.local.PreferencesManager
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * HttpClient 팩토리
 * api_spec.md 12.1 참조
 * 
 * 인터셉터, 에러 처리, 타임아웃 등을 포함한 HttpClient를 생성합니다.
 * 
 * Note: Base URL과 인증 헤더는 각 API 호출 시 직접 처리합니다.
 * (Ktor 3.x의 플러그인 API 호환성 문제로 인해 인터셉터 방식은 추후 구현)
 */
object HttpClientFactory {
    fun create(preferencesManager: PreferencesManager): HttpClient {
        return HttpClient {
            // Content Negotiation
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true  // 기본값 필드도 JSON에 포함
                    explicitNulls = false  // null 값은 JSON에 명시적으로 포함하지 않음
                })
            }
            
            // 재시도 로직
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                exponentialDelay()
            }
            
            // 타임아웃 설정
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 30000
            }
            
            // 로깅 (개발 환경에서만)
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }
}
